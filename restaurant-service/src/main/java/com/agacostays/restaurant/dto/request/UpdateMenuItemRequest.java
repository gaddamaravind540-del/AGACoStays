package com.agacostays.restaurant.dto.request;

import com.agacostays.restaurant.enums.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateMenuItemRequest {
    private String itemName;
    private MenuCategory category;
    private String description;
    @DecimalMin("0.01") private BigDecimal price;
    private Boolean availability;
    private FoodType foodType;
    private Integer preparationTimeMinutes;
}
