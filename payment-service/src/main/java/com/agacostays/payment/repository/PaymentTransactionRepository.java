package com.agacostays.payment.repository;

import com.agacostays.payment.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction,Long> {
    List<PaymentTransaction> findByPaymentIdOrderByCreatedAtDesc(Long paymentId);
}
