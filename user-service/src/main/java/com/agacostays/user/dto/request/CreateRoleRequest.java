package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
public record CreateRoleRequest(@NotBlank String roleName, String description) {}