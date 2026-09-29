package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record SendSmsRequest(
        Long branchId,
        Long customerId,
        Long bookingId,
        @NotBlank String phone,
        @NotBlank String templateCode,
        @NotNull String message,
        Map<String, Object> variables
) {}
