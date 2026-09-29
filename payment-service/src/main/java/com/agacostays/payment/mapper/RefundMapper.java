package com.agacostays.payment.mapper;
import com.agacostays.payment.dto.response.RefundResponse;
import com.agacostays.payment.entity.Refund;
import org.springframework.stereotype.Component;
@Component public class RefundMapper { public RefundResponse toResponse(Refund r){ return new RefundResponse(r.getRefundId(),r.getPaymentId(),r.getAmount(),r.getRefundStatus(),r.getRefundReason(),r.getGatewayRefundId(),r.getCreatedAt(),r.getProcessedAt()); } }
