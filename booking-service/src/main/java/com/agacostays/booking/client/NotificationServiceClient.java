package com.agacostays.booking.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "notification-service", url = "${services.notification.url}")
public interface NotificationServiceClient {
    // The source architecture names this client; actual notification API is resolved by the Notification service contract.
}
