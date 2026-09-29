package com.agacostays.booking.event;

public record BookingCreatedEvent(Long bookingId, Long branchId, Long customerId, Long roomId, java.math.BigDecimal totalAmount) {}
