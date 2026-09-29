package com.agacostays.payment.dto.response;

import java.math.BigDecimal;

public record BookingResponse(Long bookingId, Long branchId, Long customerId, BigDecimal amount, String bookingStatus, String paymentStatus) {}
