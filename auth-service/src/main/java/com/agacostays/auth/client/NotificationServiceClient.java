package com.agacostays.auth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Component
public class NotificationServiceClient {

    private final RestClient restClient;

    public NotificationServiceClient(
            RestClient.Builder builder,
            @Value("${app.clients.notification-service-url:http://localhost:8090}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public void sendPasswordResetEmail(String email, String resetToken) {
        // Optional synchronous adapter. The primary workflow uses Kafka events.
        try {
            restClient.post()
                    .uri("/api/notifications/password-reset-email")
                    .body(Map.of("email", email, "resetToken", resetToken))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ignored) {
            // Notification service availability must not break password-reset token creation.
        }
    }
}
