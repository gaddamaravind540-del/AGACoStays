package com.agacostays.notification.dto.response;

import com.agacostays.notification.enums.ReminderStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CheckoutReminderResponse(
        Long reminderId, Long branchId, Long bookingId, Long customerId,
        LocalDate checkoutDate, String checkoutTime, Integer reminderBeforeHours,
        LocalDateTime scheduledTime, LocalDateTime sentTime, ReminderStatus status
) {}
