package com.agacostays.payment.dto.request;

import jakarta.validation.constraints.*;

public record VerifyPaymentRequest(
        @NotBlank String gatewayOrderId,
        @NotBlank String gatewayPaymentId,
        @NotBlank String gatewaySignature
) {}
