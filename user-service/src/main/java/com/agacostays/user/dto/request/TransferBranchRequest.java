package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record TransferBranchRequest(@NotNull Long newBranchId, @NotNull LocalDate transferDate, String reason) {}