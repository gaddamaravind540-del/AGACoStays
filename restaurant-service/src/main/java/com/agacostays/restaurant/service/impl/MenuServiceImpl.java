package com.agacostays.restaurant.service.impl;

import com.agacostays.restaurant.dto.request.*;
import com.agacostays.restaurant.dto.response.MenuItemResponse;
import com.agacostays.restaurant.entity.RestaurantMenu;
import com.agacostays.restaurant.enums.MenuItemStatus;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.mapper.RestaurantMenuMapper;
import com.agacostays.restaurant.repository.RestaurantMenuRepository;
import com.agacostays.restaurant.repository.RestaurantRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuServiceImpl implements com.agacostays.restaurant.service.MenuService {
    private final RestaurantMenuRepository menuRepo;
    private final RestaurantRepository restaurantRepo;
    private final RestaurantMenuMapper mapper;
    private final CurrentUserProvider currentUser;

    public MenuServiceImpl(RestaurantMenuRepository menuRepo, RestaurantRepository restaurantRepo,
                           RestaurantMenuMapper mapper, CurrentUserProvider currentUser) {
        this.menuRepo=menuRepo; this.restaurantRepo=restaurantRepo; this.mapper=mapper; this.currentUser=currentUser;
    }

    @Override @Transactional
    public MenuItemResponse create(Long branchId, CreateMenuItemRequest req) {
        var restaurant = restaurantRepo.findByBranchId(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        RestaurantMenu m = RestaurantMenu.builder()
                .branchId(branchId).restaurantId(restaurant.getRestaurantId())
                .itemName(req.getItemName()).category(req.getCategory()).description(req.getDescription())
                .price(req.getPrice()).availability(req.isAvailability()).foodType(req.getFoodType())
                .preparationTimeMinutes(req.getPreparationTimeMinutes()).status(MenuItemStatus.AVAILABLE)
                .createdBy(currentUser.userId()).updatedBy(currentUser.userId()).build();
        return mapper.toResponse(menuRepo.save(m));
    }

    @Override @Transactional(readOnly=true)
    public MenuItemResponse get(Long branchId, Long menuItemId) {
        RestaurantMenu m = menuRepo.findById(menuItemId).orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        if (!m.getBranchId().equals(branchId)) throw new ResourceNotFoundException("Menu item not found for branch");
        return mapper.toResponse(m);
    }

    @Override @Transactional(readOnly=true)
    public List<MenuItemResponse> list(Long branchId, MenuSearchRequest req) {
        return menuRepo.findByBranchIdAndStatusNot(branchId, MenuItemStatus.DELETED).stream()
                .filter(m -> req.getItemName()==null || m.getItemName().toLowerCase().contains(req.getItemName().toLowerCase()))
                .filter(m -> req.getCategory()==null || m.getCategory()==req.getCategory())
                .filter(m -> req.getFoodType()==null || m.getFoodType()==req.getFoodType())
                .filter(m -> req.getAvailable()==null || m.isAvailability()==req.getAvailable())
                .map(mapper::toResponse).toList();
    }

    @Override @Transactional
    public MenuItemResponse update(Long id, UpdateMenuItemRequest req) {
        RestaurantMenu m = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        if (req.getItemName()!=null) m.setItemName(req.getItemName());
        if (req.getCategory()!=null) m.setCategory(req.getCategory());
        if (req.getDescription()!=null) m.setDescription(req.getDescription());
        if (req.getPrice()!=null) m.setPrice(req.getPrice());
        if (req.getAvailability()!=null) m.setAvailability(req.getAvailability());
        if (req.getFoodType()!=null) m.setFoodType(req.getFoodType());
        if (req.getPreparationTimeMinutes()!=null) m.setPreparationTimeMinutes(req.getPreparationTimeMinutes());
        m.setUpdatedBy(currentUser.userId());
        return mapper.toResponse(menuRepo.save(m));
    }

    @Override @Transactional
    public void delete(Long id) {
        RestaurantMenu m = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        m.setStatus(MenuItemStatus.DELETED); m.setAvailability(false); m.setUpdatedBy(currentUser.userId());
        menuRepo.save(m);
    }

    @Override @Transactional
    public MenuItemResponse updateAvailability(Long id, boolean availability) {
        RestaurantMenu m = menuRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Menu item not found"));
        if (m.getStatus()==MenuItemStatus.DELETED) throw new ResourceNotFoundException("Menu item deleted");
        m.setAvailability(availability);
        return mapper.toResponse(menuRepo.save(m));
    }
}
