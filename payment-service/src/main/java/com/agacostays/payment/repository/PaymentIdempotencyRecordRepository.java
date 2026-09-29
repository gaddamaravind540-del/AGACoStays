package com.agacostays.payment.repository;

import com.agacostays.payment.entity.PaymentIdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentIdempotencyRecordRepository extends JpaRepository<PaymentIdempotencyRecord,Long> {
    Optional<PaymentIdempotencyRecord> findByIdempotencyKey(String idempotencyKey);
}
