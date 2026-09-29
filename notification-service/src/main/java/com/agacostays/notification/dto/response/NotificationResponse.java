package com.agacostays.notification.dto.response;

import com.agacostays.notification.enums.NotificationType;
import java.time.LocalDateTime;

public record NotificationResponse(
        Long notificationId, Long branchId, Long customerId, Long bookingId,
        NotificationType notificationType, String title, String message,
        String emailTo, String emailStatus, boolean read, LocalDateTime sentAt, LocalDateTime createdAt
) {}
