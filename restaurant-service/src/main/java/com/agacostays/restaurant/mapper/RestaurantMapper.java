package com.agacostays.restaurant.mapper;

import com.agacostays.restaurant.dto.response.RestaurantResponse;
import com.agacostays.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {
    public RestaurantResponse toResponse(Restaurant r) {
        return RestaurantResponse.builder()
                .restaurantId(r.getRestaurantId()).branchId(r.getBranchId())
                .restaurantName(r.getRestaurantName()).description(r.getDescription())
                .openingTime(r.getOpeningTime()).closingTime(r.getClosingTime())
                .status(r.getStatus()).build();
    }
}
