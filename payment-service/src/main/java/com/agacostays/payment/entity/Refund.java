package com.agacostays.payment.entity;

import com.agacostays.payment.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="refunds", indexes={@Index(name="idx_refund_payment", columnList="payment_id")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Refund {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="refund_id") private Long refundId;
    @Column(name="payment_id", nullable=false) private Long paymentId;
    @Column(nullable=false, precision=14, scale=2) private BigDecimal amount;
    @Enumerated(EnumType.STRING) @Column(name="refund_status", nullable=false, length=40) private RefundStatus refundStatus;
    @Enumerated(EnumType.STRING) @Column(name="refund_reason", nullable=false, length=60) private RefundReason refundReason;
    @Column(name="gateway_refund_id", length=120) private String gatewayRefundId;
    @Column(name="created_at", nullable=false) private Instant createdAt;
    @Column(name="processed_at") private Instant processedAt;
    @PrePersist void onCreate(){ if(createdAt==null) createdAt=Instant.now(); }
}
