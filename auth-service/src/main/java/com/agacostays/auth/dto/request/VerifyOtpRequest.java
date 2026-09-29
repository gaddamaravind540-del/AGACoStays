package com.agacostays.auth.dto.request;

import com.agacostays.auth.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifyOtpRequest(
        @NotBlank String destination,
        @NotBlank String otp,
        @NotNull OtpPurpose purpose
) {}
