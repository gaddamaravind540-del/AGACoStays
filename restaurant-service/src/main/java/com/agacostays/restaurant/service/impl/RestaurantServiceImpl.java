package com.agacostays.restaurant.service.impl;

import com.agacostays.restaurant.dto.request.RestaurantRequest;
import com.agacostays.restaurant.dto.response.RestaurantResponse;
import com.agacostays.restaurant.entity.Restaurant;
import com.agacostays.restaurant.enums.RestaurantStatus;
import com.agacostays.restaurant.exception.ResourceNotFoundException;
import com.agacostays.restaurant.mapper.RestaurantMapper;
import com.agacostays.restaurant.repository.RestaurantRepository;
import com.agacostays.restaurant.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantServiceImpl implements com.agacostays.restaurant.service.RestaurantService {
    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;
    private final CurrentUserProvider currentUser;

    public RestaurantServiceImpl(RestaurantRepository repository, RestaurantMapper mapper, CurrentUserProvider currentUser) {
        this.repository=repository; this.mapper=mapper; this.currentUser=currentUser;
    }

    @Override @Transactional(readOnly=true)
    public RestaurantResponse get(Long branchId) {
        return mapper.toResponse(repository.findByBranchId(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for branch " + branchId)));
    }

    @Override @Transactional
    public RestaurantResponse create(Long branchId, RestaurantRequest request) {
        if (repository.findByBranchId(branchId).isPresent())
            throw new com.agacostays.restaurant.exception.DuplicateResourceException("Restaurant already exists for branch");
        Restaurant r = Restaurant.builder().branchId(branchId).restaurantName(request.getRestaurantName())
                .description(request.getDescription()).openingTime(request.getOpeningTime()).closingTime(request.getClosingTime())
                .status(RestaurantStatus.ACTIVE).createdBy(currentUser.userId()).updatedBy(currentUser.userId()).build();
        return mapper.toResponse(repository.save(r));
    }

    @Override @Transactional
    public RestaurantResponse update(Long branchId, RestaurantRequest request) {
        Restaurant r = repository.findByBranchId(branchId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
        r.setRestaurantName(request.getRestaurantName());
        r.setDescription(request.getDescription());
        r.setOpeningTime(request.getOpeningTime());
        r.setClosingTime(request.getClosingTime());
        r.setUpdatedBy(currentUser.userId());
        return mapper.toResponse(repository.save(r));
    }
}
