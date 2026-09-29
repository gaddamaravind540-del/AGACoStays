package com.agacostays.booking.event;

public record BookingStatusChangedEvent(Long bookingId, Long branchId, Long customerId, Long roomId, com.agacostays.booking.enums.BookingStatus status) {}
