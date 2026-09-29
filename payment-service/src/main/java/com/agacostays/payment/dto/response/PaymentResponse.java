package com.agacostays.payment.dto.response;

import com.agacostays.payment.enums.*;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(Long paymentId, Long branchId, PaymentFor paymentFor, String referenceId,
                              Long customerId, BigDecimal amount, String currency, PaymentMethod paymentMethod,
                              PaymentStatus paymentStatus, PaymentGatewayName gatewayName, String gatewayOrderId,
                              String gatewayPaymentId, Instant paidAt, Instant createdAt) {}
