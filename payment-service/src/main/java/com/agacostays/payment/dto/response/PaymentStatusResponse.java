package com.agacostays.payment.dto.response;

import com.agacostays.payment.enums.PaymentStatus;
import java.time.Instant;

public record PaymentStatusResponse(Long paymentId, PaymentStatus paymentStatus, Instant paidAt) {}
