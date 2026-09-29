package com.agacostays.restaurant.dto.request;

import com.agacostays.restaurant.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateMenuItemRequest {
    @NotBlank private String itemName;
    @NotNull private MenuCategory category;
    private String description;
    @NotNull @DecimalMin("0.01") private BigDecimal price;
    private boolean availability = true;
    @NotNull private FoodType foodType;
    @Min(0) private Integer preparationTimeMinutes;
}
