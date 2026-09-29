package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.request.RestaurantFeedbackRequest;
import com.agacostays.restaurant.dto.response.RestaurantFeedbackResponse;
import java.util.List;

public interface RestaurantFeedbackService {
    RestaurantFeedbackResponse create(Long branchId, RestaurantFeedbackRequest request);
    List<RestaurantFeedbackResponse> byBranch(Long branchId);
}
