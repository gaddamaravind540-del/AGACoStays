package com.agacostays.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name="payment_transactions", indexes={@Index(name="idx_tx_payment", columnList="payment_id")})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentTransaction {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="transaction_id") private Long transactionId;
    @Column(name="payment_id", nullable=false) private Long paymentId;
    @Column(name="transaction_type", nullable=false, length=40) private String transactionType;
    @Column(nullable=false, precision=14, scale=2) private BigDecimal amount;
    @Column(name="gateway_transaction_id", length=120) private String gatewayTransactionId;
    @Column(nullable=false, length=40) private String status;
    @Column(name="created_at", nullable=false) private Instant createdAt;
    @PrePersist void onCreate(){ if(createdAt==null) createdAt=Instant.now(); }
}
