package com.agacostays.restaurant.dto.response;

import com.agacostays.restaurant.enums.RestaurantStatus;
import lombok.*;

import java.time.LocalTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantResponse {
    private Long restaurantId;
    private Long branchId;
    private String restaurantName;
    private String description;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private RestaurantStatus status;
}
