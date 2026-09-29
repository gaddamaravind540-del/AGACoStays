package com.agacostays.notification.mapper;

import com.agacostays.notification.dto.response.NotificationResponse;
import com.agacostays.notification.entity.NotificationLog;
import org.springframework.stereotype.Component;

@Component
public class NotificationLogMapper {
    public NotificationResponse toResponse(NotificationLog n) {
        return new NotificationResponse(n.getNotificationId(), n.getBranchId(), n.getCustomerId(), n.getBookingId(), n.getNotificationType(), n.getTitle(), n.getMessage(), n.getEmailTo(), n.getEmailStatus(), n.isRead(), n.getSentAt(), n.getCreatedAt());
    }
}
