package com.agacostays.restaurant.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantFeedbackRequest {
    @NotNull private Long orderId;
    @NotNull @Min(1) @Max(5) private Integer rating;
    private String comments;
}
