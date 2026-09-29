package com.agacostays.payment.dto.response;

import java.math.BigDecimal;

public record BillingResponse(Long bookingId, Long branchId, Long customerId, BigDecimal finalAmount, String paymentStatus) {}
