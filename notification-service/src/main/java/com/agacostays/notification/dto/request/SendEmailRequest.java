package com.agacostays.notification.dto.request;

import com.agacostays.notification.enums.NotificationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record SendEmailRequest(
        Long branchId,
        Long customerId,
        Long bookingId,
        @NotBlank @Email String to,
        @NotBlank String templateCode,
        String subject,
        @NotNull String message,
        NotificationType notificationType,
        Map<String, Object> variables
) {}
