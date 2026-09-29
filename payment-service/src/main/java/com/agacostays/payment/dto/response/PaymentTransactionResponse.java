package com.agacostays.payment.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentTransactionResponse(Long transactionId, Long paymentId, String transactionType,
                                         BigDecimal amount, String gatewayTransactionId, String status, Instant createdAt) {}
