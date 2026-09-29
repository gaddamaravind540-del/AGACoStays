package com.agacostays.user.dto.request;
import com.agacostays.user.enums.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record CreateStaffRequest(
 @NotBlank @Size(min=2,max=120) String fullName,
 @NotBlank @Email String email,
 @Size(max=20) String phone,
 @NotBlank @Size(min=8,max=100) String password,
 @NotNull Department department,
 @NotNull Shift shift,
 @NotNull LocalDate joiningDate,
 @NotBlank String roleName
) {}