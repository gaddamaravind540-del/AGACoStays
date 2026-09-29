package com.agacostays.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name="payment_idempotency_records", uniqueConstraints=@UniqueConstraint(name="uk_idempotency_key", columnNames="idempotency_key"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentIdempotencyRecord {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(name="idempotency_key", nullable=false, length=160) private String idempotencyKey;
    @Column(name="request_hash", nullable=false, length=64) private String requestHash;
    @Column(name="payment_id") private Long paymentId;
    @Column(name="created_at", nullable=false) private Instant createdAt;
    @Column(name="expires_at", nullable=false) private Instant expiresAt;
}
