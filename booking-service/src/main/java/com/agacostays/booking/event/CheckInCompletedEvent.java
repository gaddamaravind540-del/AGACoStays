package com.agacostays.booking.event;

public record CheckInCompletedEvent(Long bookingId, Long branchId, Long customerId, Long roomId) {}
