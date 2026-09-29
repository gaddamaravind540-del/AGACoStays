package com.agacostays.payment.dto.request;

import com.agacostays.payment.enums.RefundReason;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record RefundRequest(
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull RefundReason reason
) {}
