package com.agacostays.restaurant.dto.request;

import com.agacostays.restaurant.enums.*;
import lombok.Data;

@Data
public class MenuSearchRequest {
    private String itemName;
    private MenuCategory category;
    private FoodType foodType;
    private Boolean available;
}
