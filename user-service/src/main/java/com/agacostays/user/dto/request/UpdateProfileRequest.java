package com.agacostays.user.dto.request;
import jakarta.validation.constraints.*;
public record UpdateProfileRequest(@NotBlank String fullName, String phone, String address) {}