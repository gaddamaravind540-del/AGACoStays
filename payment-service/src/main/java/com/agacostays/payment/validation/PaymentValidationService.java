package com.agacostays.payment.validation;
import com.agacostays.payment.entity.*; import com.agacostays.payment.enums.*; import com.agacostays.payment.exception.*; import com.agacostays.payment.repository.*; import org.springframework.stereotype.Service; import java.math.*; import java.util.*;
@Service public class PaymentValidationService {
 private final RefundRepository refunds; public PaymentValidationService(RefundRepository r){this.refunds=r;}
 public void validateCreate(BigDecimal amount, PaymentFor forType){if(amount==null||amount.signum()<=0)throw new BusinessRuleException("Payment amount must be positive");if(forType==null)throw new BusinessRuleException("paymentFor is required");}
 public void validateRefund(Payment p, BigDecimal amount){if(p.getPaymentStatus()!=PaymentStatus.SUCCESS&&p.getPaymentStatus()!=PaymentStatus.PARTIALLY_REFUNDED)throw new InvalidStatusException("Only successful payments can be refunded"); BigDecimal already=refunds.findByPaymentIdAndRefundStatusIn(p.getPaymentId(), List.of(RefundStatus.REQUESTED,RefundStatus.PROCESSING,RefundStatus.PROCESSED)).stream().map(Refund::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add); if(amount.compareTo(p.getAmount().subtract(already))>0)throw new BusinessRuleException("Refund amount exceeds refundable amount");}
}
