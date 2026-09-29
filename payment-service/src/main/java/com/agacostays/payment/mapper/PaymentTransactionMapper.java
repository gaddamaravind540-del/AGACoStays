package com.agacostays.payment.mapper;
import com.agacostays.payment.dto.response.PaymentTransactionResponse;
import com.agacostays.payment.entity.PaymentTransaction;
import org.springframework.stereotype.Component;
@Component public class PaymentTransactionMapper { public PaymentTransactionResponse toResponse(PaymentTransaction t){ return new PaymentTransactionResponse(t.getTransactionId(),t.getPaymentId(),t.getTransactionType(),t.getAmount(),t.getGatewayTransactionId(),t.getStatus(),t.getCreatedAt()); } }
