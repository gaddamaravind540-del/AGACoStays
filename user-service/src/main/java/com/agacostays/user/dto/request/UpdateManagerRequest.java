package com.agacostays.user.dto.request;
import com.agacostays.user.enums.ManagerAccessLevel;
import jakarta.validation.constraints.*;
public record UpdateManagerRequest(
 @NotBlank @Size(min=2,max=120) String fullName,
 @Size(max=20) String phone,
 ManagerAccessLevel accessLevel
) {}