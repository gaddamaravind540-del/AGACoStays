package com.agacostays.restaurant.service.impl;
import com.agacostays.restaurant.dto.request.DishPhotoRequest;
import com.agacostays.restaurant.dto.response.DishPhotoResponse;
import com.agacostays.restaurant.entity.RestaurantMenuPhoto;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.mapper.RestaurantMenuPhotoMapper;
import com.agacostays.restaurant.repository.RestaurantMenuPhotoRepository;
import com.agacostays.restaurant.repository.RestaurantMenuRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DishPhotoServiceImpl implements com.agacostays.restaurant.service.DishPhotoService {
    private final RestaurantMenuPhotoRepository photoRepo;
    private final RestaurantMenuRepository menuRepo;
    private final RestaurantMenuPhotoMapper mapper;
    private final CurrentUserProvider currentUser;

    public DishPhotoServiceImpl(RestaurantMenuPhotoRepository photoRepo, RestaurantMenuRepository menuRepo,
                                RestaurantMenuPhotoMapper mapper, CurrentUserProvider currentUser) {
        this.photoRepo=photoRepo; this.menuRepo=menuRepo; this.mapper=mapper; this.currentUser=currentUser;
    }

    @Override @Transactional
    public DishPhotoResponse add(Long branchId, Long menuItemId, DishPhotoRequest req) {
        var menu = menuRepo.findById(menuItemId).orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        if (!menu.getBranchId().equals(branchId)) throw new ResourceNotFoundException("Menu item not found for branch");
        if (req.isPrimaryPhoto()) {
            photoRepo.findByMenuItemId(menuItemId).forEach(p -> { p.setPrimaryPhoto(false); photoRepo.save(p); });
        }
        var p = RestaurantMenuPhoto.builder().branchId(branchId).restaurantId(menu.getRestaurantId())
                .menuItemId(menuItemId).photoUrl(req.getPhotoUrl()).caption(req.getCaption())
                .primaryPhoto(req.isPrimaryPhoto()).uploadedBy(currentUser.userId()).build();
        return mapper.toResponse(photoRepo.save(p));
    }

    @Override @Transactional(readOnly=true)
    public List<DishPhotoResponse> list(Long menuItemId) {
        return photoRepo.findByMenuItemId(menuItemId).stream().map(mapper::toResponse).toList();
    }

    @Override @Transactional
    public void delete(Long photoId) {
        if (!photoRepo.existsById(photoId)) throw new ResourceNotFoundException("Photo not found");
        photoRepo.deleteById(photoId);
    }
}
