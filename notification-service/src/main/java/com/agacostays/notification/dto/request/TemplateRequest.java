package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TemplateRequest(
        @NotBlank String code,
        @NotBlank String subject,
        @NotBlank String body,
        Boolean active
) {}
