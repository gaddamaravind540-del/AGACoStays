package com.agacostays.billing.dto.request; import jakarta.validation.constraints.NotBlank; public record PayFinalBillRequest(@NotBlank String paymentMethod){}
