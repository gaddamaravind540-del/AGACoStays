package com.agacostays.notification.repository;

import com.agacostays.notification.entity.CheckoutReminder;
import com.agacostays.notification.enums.ReminderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface CheckoutReminderRepository extends JpaRepository<CheckoutReminder, Long> {
    List<CheckoutReminder> findByStatusAndScheduledTimeLessThanEqualOrderByScheduledTimeAsc(ReminderStatus status, LocalDateTime time, Pageable pageable);
    List<CheckoutReminder> findByBookingIdOrderByScheduledTimeDesc(Long bookingId);
}
