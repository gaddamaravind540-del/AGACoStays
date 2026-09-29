package com.agacostays.support.dto.response;

import lombok.*;
import java.time.OffsetDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantFeedbackResponse {
    private Long feedbackId;
    private Long branchId;
    private Long bookingId;
    private Long orderId;
    private Long customerId;
    private Integer foodRating;
    private Integer tasteRating;
    private Integer deliveryRating;
    private String comments;
    private OffsetDateTime createdAt;
}
