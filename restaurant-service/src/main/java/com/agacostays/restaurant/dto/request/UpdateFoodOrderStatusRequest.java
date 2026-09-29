package com.agacostays.restaurant.dto.request;

import com.agacostays.restaurant.enums.FoodOrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateFoodOrderStatusRequest {
    @NotNull private FoodOrderStatus status;
    private String remarks;
}
