package com.agacostays.payment.service;
import com.agacostays.payment.dto.response.GatewayOrderResponse; import java.math.BigDecimal;
public interface PaymentGatewayService { GatewayOrderResponse createOrder(BigDecimal amount,String currency,String receipt); boolean verifySignature(String orderId,String paymentId,String signature); String createRefund(String gatewayPaymentId,BigDecimal amount); }
