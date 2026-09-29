package com.agacostays.support.event;

public record FeedbackSubmittedEvent(Long feedbackId, Long branchId, Long bookingId, Long customerId) {}
