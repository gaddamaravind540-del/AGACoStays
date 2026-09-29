package com.agacostays.notification.event;

public record NotificationFailedEvent(Long notificationId, String reason) {}
