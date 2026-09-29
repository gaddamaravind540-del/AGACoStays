package com.agacostays.support.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FeedbackResponse {
    private Long feedbackId;
    private Long branchId;
    private Long bookingId;
    private Long customerId;
    private Integer hotelRating;
    private String hotelComments;
    private Integer restaurantRating;
    private String restaurantComments;
    private String feedbackType;
    private OffsetDateTime createdAt;
}
