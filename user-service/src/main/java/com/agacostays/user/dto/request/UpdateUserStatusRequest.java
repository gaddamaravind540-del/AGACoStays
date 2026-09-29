package com.agacostays.user.dto.request;
import com.agacostays.user.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateUserStatusRequest(@NotNull UserStatus status) {}