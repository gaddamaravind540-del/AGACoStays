package com.agacostays.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name="payment_webhook_logs", indexes={@Index(name="idx_webhook_received", columnList="received_at")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentWebhookLog {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="webhook_log_id") private Long webhookLogId;
    @Column(name="event_type", nullable=false, length=60) private String eventType;
    @Column(name="gateway_order_id", length=120) private String gatewayOrderId;
    @Column(name="gateway_payment_id", length=120) private String gatewayPaymentId;
    @Column(name="signature", length=512) private String signature;
    @Column(name="raw_payload", nullable=false, columnDefinition="TEXT") private String rawPayload;
    @Column(nullable=false) private boolean verified;
    @Column(name="received_at", nullable=false) private Instant receivedAt;
    @Column(name="processed_at") private Instant processedAt;
}
