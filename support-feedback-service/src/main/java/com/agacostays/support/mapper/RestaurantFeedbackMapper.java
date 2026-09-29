package com.agacostays.support.mapper;
import com.agacostays.support.dto.response.RestaurantFeedbackResponse;
import com.agacostays.support.entity.RestaurantFeedback;
import org.springframework.stereotype.Component;

@Component
public class RestaurantFeedbackMapper {
    public RestaurantFeedbackResponse toResponse(RestaurantFeedback x) {
        return RestaurantFeedbackResponse.builder().feedbackId(x.getFeedbackId()).branchId(x.getBranchId())
                .bookingId(x.getBookingId()).orderId(x.getOrderId()).customerId(x.getCustomerId())
                .foodRating(x.getFoodRating()).tasteRating(x.getTasteRating())
                .deliveryRating(x.getDeliveryRating()).comments(x.getComments()).createdAt(x.getCreatedAt())
                .build();
    }
}
