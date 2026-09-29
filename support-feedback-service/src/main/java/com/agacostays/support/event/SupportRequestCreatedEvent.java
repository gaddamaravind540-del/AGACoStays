package com.agacostays.support.event;

public record SupportRequestCreatedEvent(Long requestId, Long branchId, Long customerId, Long bookingId, String priority) {}
