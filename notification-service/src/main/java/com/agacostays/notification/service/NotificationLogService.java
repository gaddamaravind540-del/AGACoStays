package com.agacostays.notification.service;

import com.agacostays.notification.dto.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationLogService {
    Page<NotificationResponse> search(Long branchId, Pageable pageable);
}
