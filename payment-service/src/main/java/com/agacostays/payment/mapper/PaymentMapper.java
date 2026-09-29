package com.agacostays.payment.mapper;

import com.agacostays.payment.dto.response.PaymentResponse;
import com.agacostays.payment.entity.Payment;
import org.springframework.stereotype.Component;

@Component public class PaymentMapper {
 public PaymentResponse toResponse(Payment p){ return new PaymentResponse(p.getPaymentId(),p.getBranchId(),p.getPaymentFor(),p.getReferenceId(),p.getCustomerId(),p.getAmount(),p.getCurrency(),p.getPaymentMethod(),p.getPaymentStatus(),p.getGatewayName(),p.getGatewayOrderId(),p.getGatewayPaymentId(),p.getPaidAt(),p.getCreatedAt()); }
}
