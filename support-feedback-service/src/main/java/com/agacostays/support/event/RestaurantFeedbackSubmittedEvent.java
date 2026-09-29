package com.agacostays.support.event;

public record RestaurantFeedbackSubmittedEvent(Long feedbackId, Long branchId, Long bookingId, Long orderId, Long customerId) {}
