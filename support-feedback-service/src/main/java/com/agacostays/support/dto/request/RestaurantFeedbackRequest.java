package com.agacostays.support.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestaurantFeedbackRequest {
    @NotNull private Long orderId;
    @NotNull @Min(1) @Max(5) private Integer foodRating;
    @NotNull @Min(1) @Max(5) private Integer tasteRating;
    @NotNull @Min(1) @Max(5) private Integer deliveryRating;
    @Size(max=2000) private String comments;
}
