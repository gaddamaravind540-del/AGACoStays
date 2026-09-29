package com.agacostays.payment.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record RestaurantPaymentRequest(
        @NotNull Long branchId,
        @NotBlank String orderId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank @Size(max=8) String currency
) {}
