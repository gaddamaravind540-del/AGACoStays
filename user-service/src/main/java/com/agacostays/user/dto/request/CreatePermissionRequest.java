package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
public record CreatePermissionRequest(
 @NotBlank String permissionCode,
 @NotBlank String permissionName,
 String description,
 @NotBlank String moduleName
) {}