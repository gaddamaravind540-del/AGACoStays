package com.agacostays.restaurant.dto.response;

import com.agacostays.restaurant.enums.*;
import lombok.*;

import java.math.BigDecimal;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MenuItemResponse {
    private Long menuItemId;
    private Long branchId;
    private Long restaurantId;
    private String itemName;
    private MenuCategory category;
    private String description;
    private BigDecimal price;
    private boolean availability;
    private FoodType foodType;
    private Integer preparationTimeMinutes;
    private MenuItemStatus status;
}
