package com.agacostays.booking.event;

public record BookingApprovedEvent(Long bookingId, Long branchId, Long customerId, Long roomId) {}
