package com.agacostays.auth.dto.request;

import com.agacostays.auth.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SendOtpRequest(
        @NotBlank String destination,
        @NotNull OtpPurpose purpose
) {}
