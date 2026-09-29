package com.agacostays.payment.repository;

import com.agacostays.payment.entity.PaymentWebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentWebhookLogRepository extends JpaRepository<PaymentWebhookLog,Long> {
    boolean existsByGatewayPaymentIdAndEventType(String gatewayPaymentId, String eventType);
}
