package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CheckoutReminderRequest(
        @NotNull Long branchId,
        @NotNull Long bookingId,
        @NotNull Long customerId,
        @NotNull LocalDate checkoutDate,
        @NotNull String checkoutTime,
        @NotNull Integer reminderBeforeHours,
        @NotNull LocalDateTime scheduledTime
) {}
