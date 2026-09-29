package com.agacostays.user.dto.request;
import jakarta.validation.constraints.NotNull;
public record AssignPermissionRequest(@NotNull Long permissionId) {}