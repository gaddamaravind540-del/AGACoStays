package com.agacostays.notification.service;

import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {
    EmailResponse sendEmail(SendEmailRequest request);
    Page<NotificationResponse> myNotifications(Long customerId, Pageable pageable);
    NotificationResponse markRead(Long notificationId, Long customerId, boolean rootAdmin);
}
