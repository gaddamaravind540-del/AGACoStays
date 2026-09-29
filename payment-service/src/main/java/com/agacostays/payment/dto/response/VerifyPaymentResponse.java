package com.agacostays.payment.dto.response;

public record VerifyPaymentResponse(boolean verified, PaymentResponse payment) {}
