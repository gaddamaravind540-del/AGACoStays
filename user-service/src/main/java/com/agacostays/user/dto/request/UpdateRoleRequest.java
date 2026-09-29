package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
public record UpdateRoleRequest(@NotBlank String description, @NotBlank String status) {}