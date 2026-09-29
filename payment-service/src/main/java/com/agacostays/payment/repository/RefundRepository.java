package com.agacostays.payment.repository;

import com.agacostays.payment.entity.Refund;
import com.agacostays.payment.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import java.math.BigDecimal;

public interface RefundRepository extends JpaRepository<Refund,Long> {
    List<Refund> findByPaymentIdOrderByCreatedAtDesc(Long paymentId);
    List<Refund> findByPaymentIdAndRefundStatusIn(Long paymentId, Collection<RefundStatus> statuses);
    java.util.Optional<Refund> findFirstByPaymentIdAndGatewayRefundId(Long paymentId, String gatewayRefundId);
}
