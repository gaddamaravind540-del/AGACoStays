package com.agacostays.restaurant.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantRequest {
    @NotBlank private String restaurantName;
    private String description;
    private LocalTime openingTime;
    private LocalTime closingTime;
}
