package com.agacostays.payment.dto.request;

import com.agacostays.payment.enums.WebhookEventType;

public record PaymentWebhookRequest(
        WebhookEventType eventType,
        String gatewayOrderId,
        String gatewayPaymentId,
        String signature,
        String gatewayRefundId,
        String paymentStatus
) {}
