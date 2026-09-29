package com.agacostays.booking.event;

public record BookingPaymentStatusUpdatedEvent(Long bookingId, Long branchId, Long customerId, com.agacostays.booking.enums.PaymentStatus paymentStatus) {}
