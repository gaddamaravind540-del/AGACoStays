package com.agacostays.restaurant.mapper;
import com.agacostays.restaurant.dto.response.RestaurantFeedbackResponse;
import com.agacostays.restaurant.entity.RestaurantFeedback;
import org.springframework.stereotype.Component;
@Component
public class RestaurantFeedbackMapper {
    public RestaurantFeedbackResponse toResponse(RestaurantFeedback f) {
        return new RestaurantFeedbackResponse(f.getFeedbackId(), f.getOrderId(), f.getRating(), f.getComments());
    }
}
