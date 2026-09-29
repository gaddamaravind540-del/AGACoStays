package com.agacostays.payment.config;
import lombok.*; import org.springframework.boot.context.properties.ConfigurationProperties;
@Getter @Setter @ConfigurationProperties(prefix="payment.gateway") public class PaymentGatewayProperties { private String name; private String keyId; private String secret; private String webhookSecret; private String currency; private boolean mockEnabled=true; }
