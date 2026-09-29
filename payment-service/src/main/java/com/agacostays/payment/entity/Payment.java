package com.agacostays.payment.entity;

import com.agacostays.payment.enums.*;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments", indexes = {
        @Index(name = "idx_payment_customer", columnList = "customer_id"),
        @Index(name = "idx_payment_branch", columnList = "branch_id"),
        @Index(name = "idx_payment_reference", columnList = "reference_id"),
        @Index(name = "idx_payment_gateway_order", columnList = "gateway_order_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;
    @Column(name = "branch_id", nullable = false) private Long branchId;
    @Enumerated(EnumType.STRING) @Column(name = "payment_for", nullable = false, length = 40) private PaymentFor paymentFor;
    @Column(name = "reference_id", nullable = false, length = 100) private String referenceId;
    @Column(name = "customer_id", nullable = false) private Long customerId;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal amount;
    @Column(nullable = false, length = 8) private String currency;
    @Enumerated(EnumType.STRING) @Column(name = "payment_method", nullable = false, length = 40) private PaymentMethod paymentMethod;
    @Enumerated(EnumType.STRING) @Column(name = "payment_status", nullable = false, length = 40) private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING) @Column(name = "gateway_name", nullable = false, length = 40) private PaymentGatewayName gatewayName;
    @Column(name = "gateway_order_id", length = 120) private String gatewayOrderId;
    @Column(name = "gateway_payment_id", length = 120) private String gatewayPaymentId;
    @Column(name = "gateway_signature", length = 512) private String gatewaySignature;
    @Column(name = "paid_at") private Instant paidAt;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;

    @PrePersist void onCreate() { Instant now=Instant.now(); if(createdAt==null)createdAt=now; if(updatedAt==null)updatedAt=now; }
    @PreUpdate void onUpdate() { updatedAt=Instant.now(); }
}
