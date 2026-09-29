package com.agacostays.payment.config;
import org.springframework.boot.context.properties.EnableConfigurationProperties; import org.springframework.context.annotation.*; import org.springframework.web.client.RestClient;
@Configuration @EnableConfigurationProperties(PaymentGatewayProperties.class) public class AppConfig { @Bean RestClient.Builder restClientBuilder(){return RestClient.builder();} }
