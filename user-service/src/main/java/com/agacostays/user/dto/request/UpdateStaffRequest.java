package com.agacostays.user.dto.request;
import com.agacostays.user.enums.*;
import jakarta.validation.constraints.*;
public record UpdateStaffRequest(
 @NotBlank String fullName,
 String phone,
 @NotNull Department department,
 @NotNull Shift shift
) {}