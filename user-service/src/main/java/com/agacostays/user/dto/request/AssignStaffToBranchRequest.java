package com.agacostays.user.dto.request;
import com.agacostays.user.enums.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record AssignStaffToBranchRequest(
 @NotNull Long staffId,
 @NotNull Long branchId,
 @NotBlank String roleName,
 @NotNull Department department,
 @NotNull Shift shift,
 @NotNull LocalDate assignedFrom
) {}