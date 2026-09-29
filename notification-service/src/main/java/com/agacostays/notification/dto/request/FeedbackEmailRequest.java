package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FeedbackEmailRequest(
        Long branchId, Long customerId, Long bookingId,
        @NotBlank @Email String email,
        @NotBlank String customerName,
        String feedbackType,
        String message
) {}
