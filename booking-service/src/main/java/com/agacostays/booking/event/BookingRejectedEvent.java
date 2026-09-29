package com.agacostays.booking.event;

public record BookingRejectedEvent(Long bookingId, Long branchId, Long customerId, Long roomId, String remarks) {}
