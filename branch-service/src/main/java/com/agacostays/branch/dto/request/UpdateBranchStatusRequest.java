package com.agacostays.branch.dto.request;
import com.agacostays.branch.enums.BranchStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateBranchStatusRequest(@NotNull BranchStatus status) {}
