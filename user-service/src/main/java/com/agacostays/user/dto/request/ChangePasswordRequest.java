package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
public record ChangePasswordRequest(@NotBlank String oldPassword, @NotBlank @Size(min=8,max=100) String newPassword) {}