package com.agacostays.support.mapper;
import com.agacostays.support.dto.response.FeedbackResponse;
import com.agacostays.support.entity.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public FeedbackResponse toResponse(Feedback x) {
        return FeedbackResponse.builder().feedbackId(x.getFeedbackId()).branchId(x.getBranchId())
                .bookingId(x.getBookingId()).customerId(x.getCustomerId()).hotelRating(x.getHotelRating())
                .hotelComments(x.getHotelComments()).restaurantRating(x.getRestaurantRating())
                .restaurantComments(x.getRestaurantComments()).feedbackType(x.getFeedbackType())
                .createdAt(x.getCreatedAt()).build();
    }
}
