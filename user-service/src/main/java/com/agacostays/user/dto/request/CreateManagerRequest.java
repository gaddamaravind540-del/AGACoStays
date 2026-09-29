package com.agacostays.user.dto.request;
import com.agacostays.user.enums.ManagerAccessLevel;
import jakarta.validation.constraints.*;
public record CreateManagerRequest(
 @NotBlank @Size(min=2,max=120) String fullName,
 @NotBlank @Email String email,
 @Size(max=20) String phone,
 @NotBlank @Size(min=8,max=100) String password,
 ManagerAccessLevel accessLevel
) {}