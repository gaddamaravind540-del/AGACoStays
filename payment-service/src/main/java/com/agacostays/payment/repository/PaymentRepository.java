package com.agacostays.payment.repository;

import com.agacostays.payment.entity.Payment;
import com.agacostays.payment.enums.PaymentStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Optional<Payment> findFirstByGatewayOrderId(String gatewayOrderId);
    List<Payment> findByReferenceIdOrderByCreatedAtDesc(String referenceId);
    Page<Payment> findByCustomerIdOrderByCreatedAtDesc(Long customerId, Pageable pageable);
    Page<Payment> findByBranchIdOrderByCreatedAtDesc(Long branchId, Pageable pageable);
    Optional<Payment> findFirstByReferenceIdAndPaymentForOrderByCreatedAtDesc(String referenceId, com.agacostays.payment.enums.PaymentFor paymentFor);
    List<Payment> findByPaymentStatusAndCreatedAtBefore(PaymentStatus status, java.time.Instant cutoff);
}
