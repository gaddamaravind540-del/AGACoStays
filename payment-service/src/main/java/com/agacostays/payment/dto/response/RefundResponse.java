package com.agacostays.payment.dto.response;

import com.agacostays.payment.enums.*;
import java.math.BigDecimal;
import java.time.Instant;

public record RefundResponse(Long refundId, Long paymentId, BigDecimal amount, RefundStatus refundStatus,
                             RefundReason refundReason, String gatewayRefundId, Instant createdAt, Instant processedAt) {}
