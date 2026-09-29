package com.agacostays.auth.producer;

import com.agacostays.auth.constants.KafkaTopicConstants;
import com.agacostays.auth.event.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public AuthEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void userRegistered(UserRegisteredEvent event) {
        safeSend(KafkaTopicConstants.USER_REGISTERED, String.valueOf(event.userId()), event);
    }

    public void loginSuccess(LoginSuccessEvent event) {
        safeSend(KafkaTopicConstants.LOGIN_SUCCESS, String.valueOf(event.userId()), event);
    }

    public void loginFailed(LoginFailedEvent event) {
        safeSend(KafkaTopicConstants.LOGIN_FAILED, event.email(), event);
    }

    public void passwordResetRequested(PasswordResetRequestedEvent event) {
        safeSend(KafkaTopicConstants.PASSWORD_RESET_REQUESTED, String.valueOf(event.userId()), event);
    }

    private void safeSend(String topic, String key, Object event) {
        try {
            kafkaTemplate.send(topic, key, event);
        } catch (Exception ignored) {
            // Authentication must still work when Kafka is temporarily unavailable.
            // Production deployments can add an outbox/retry mechanism.
        }
    }
}
