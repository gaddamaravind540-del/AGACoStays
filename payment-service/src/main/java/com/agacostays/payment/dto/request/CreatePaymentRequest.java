package com.agacostays.payment.dto.request;

import com.agacostays.payment.enums.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreatePaymentRequest(
        @NotNull Long branchId,
        @NotNull PaymentFor paymentFor,
        @NotBlank String referenceId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank @Size(max=8) String currency,
        @NotNull PaymentMethod paymentMethod
) {}
