package com.agacostays.support.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class FeedbackRequest {
    @NotNull private Long bookingId;
    @Min(1) @Max(5) private Integer hotelRating;
    @Size(max=2000) private String hotelComments;
    @Min(1) @Max(5) private Integer restaurantRating;
    @Size(max=2000) private String restaurantComments;
    private String feedbackType;
}
