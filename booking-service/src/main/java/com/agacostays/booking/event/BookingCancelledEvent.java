package com.agacostays.booking.event;

public record BookingCancelledEvent(Long bookingId, Long branchId, Long customerId, Long roomId, String reason) {}
