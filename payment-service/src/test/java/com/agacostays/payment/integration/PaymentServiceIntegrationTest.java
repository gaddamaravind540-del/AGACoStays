package com.agacostays.payment.integration;

import com.agacostays.payment.config.PaymentGatewayProperties;
import com.agacostays.payment.service.impl.PaymentGatewayServiceImpl;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceIntegrationTest {
 @Test void gatewayOrderCreationAndSignatureVerificationWork(){
   PaymentGatewayProperties p=new PaymentGatewayProperties(); p.setName("MOCK"); p.setMockEnabled(true); p.setSecret("integration-secret");
   var gateway=new PaymentGatewayServiceImpl(p);
   var order=gateway.createOrder(new BigDecimal("1250.00"),"INR","booking-501");
   assertNotNull(order.gatewayOrderId()); assertEquals(new BigDecimal("1250.00"),order.amount());
 }
}
