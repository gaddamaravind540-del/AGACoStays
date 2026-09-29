package com.agacostays.payment.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record FinalBillPaymentRequest(
        @NotNull Long branchId,
        @NotNull Long bookingId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank @Size(max=8) String currency
) {}
