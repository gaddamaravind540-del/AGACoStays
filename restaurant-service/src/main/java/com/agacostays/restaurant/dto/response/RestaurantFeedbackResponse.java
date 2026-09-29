package com.agacostays.restaurant.dto.response;

public record RestaurantFeedbackResponse(Long feedbackId, Long orderId, Integer rating, String comments) {}
