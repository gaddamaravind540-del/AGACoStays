package com.agacostays.restaurant.client;
import org.springframework.cloud.openfeign.FeignClient;
@FeignClient(name="notification-service", url="${services.notification.url}")
public interface NotificationServiceClient {}
