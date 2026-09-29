package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public record CheckoutReminderSendRequest(@NotNull Long bookingId, Long branchId, Long customerId, @Email String email) {}
