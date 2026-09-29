package com.agacostays.support.service;

import com.agacostays.support.dto.request.RestaurantFeedbackRequest;
import com.agacostays.support.dto.response.RestaurantFeedbackResponse;
import java.util.List;

public interface RestaurantFeedbackService {
    RestaurantFeedbackResponse create(RestaurantFeedbackRequest request);
    List<RestaurantFeedbackResponse> byMyOrders();
    List<RestaurantFeedbackResponse> byBranch(Long branchId);
    List<RestaurantFeedbackResponse> byOrder(Long orderId);
}
