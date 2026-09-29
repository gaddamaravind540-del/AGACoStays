package com.agacostays.notification.event;

public record NotificationSentEvent(Long notificationId, Long customerId, Long bookingId, String notificationType) {}
