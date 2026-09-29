package com.agacostays.payment.config;
import org.apache.kafka.clients.admin.NewTopic; import org.springframework.context.annotation.*;
@Configuration public class KafkaTopicConfig {
 @Bean NewTopic paymentSuccessTopic(){return new NewTopic("payment.success",3,(short)1);} @Bean NewTopic paymentFailedTopic(){return new NewTopic("payment.failed",3,(short)1);} @Bean NewTopic refundProcessedTopic(){return new NewTopic("payment.refund.processed",3,(short)1);} @Bean NewTopic refundFailedTopic(){return new NewTopic("payment.refund.failed",3,(short)1);} @Bean NewTopic paymentWebhookTopic(){return new NewTopic("payment.webhook.received",3,(short)1);}
}
