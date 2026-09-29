package com.agacostays.restaurant.mapper;

import com.agacostays.restaurant.dto.response.MenuItemResponse;
import com.agacostays.restaurant.entity.RestaurantMenu;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuMapper {
    public MenuItemResponse toResponse(RestaurantMenu m) {
        return MenuItemResponse.builder()
                .menuItemId(m.getMenuItemId()).branchId(m.getBranchId()).restaurantId(m.getRestaurantId())
                .itemName(m.getItemName()).category(m.getCategory()).description(m.getDescription())
                .price(m.getPrice()).availability(m.isAvailability()).foodType(m.getFoodType())
                .preparationTimeMinutes(m.getPreparationTimeMinutes()).status(m.getStatus()).build();
    }
}
