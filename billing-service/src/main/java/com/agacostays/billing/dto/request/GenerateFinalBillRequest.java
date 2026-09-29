package com.agacostays.billing.dto.request; import jakarta.validation.constraints.NotNull; public record GenerateFinalBillRequest(@NotNull Long branchId,@NotNull Long customerId){}
