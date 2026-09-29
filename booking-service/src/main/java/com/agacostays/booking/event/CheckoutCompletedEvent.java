package com.agacostays.booking.event;

public record CheckoutCompletedEvent(Long bookingId, Long branchId, Long customerId, Long roomId) {}
