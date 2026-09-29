package com.agacostays.notification.repository;

import com.agacostays.notification.entity.NotificationLog;
import com.agacostays.notification.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    Page<NotificationLog> findByCustomerIdOrderByCreatedAtDesc(Long customerId, Pageable pageable);
    Page<NotificationLog> findByBranchIdAndCustomerIdOrderByCreatedAtDesc(Long branchId, Long customerId, Pageable pageable);
    Page<NotificationLog> findByBranchIdOrderByCreatedAtDesc(Long branchId, Pageable pageable);
    Page<NotificationLog> findByNotificationTypeOrderByCreatedAtDesc(NotificationType type, Pageable pageable);
}
