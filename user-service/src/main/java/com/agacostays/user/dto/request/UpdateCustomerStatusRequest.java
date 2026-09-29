package com.agacostays.user.dto.request;
import com.agacostays.user.enums.CustomerStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateCustomerStatusRequest(@NotNull CustomerStatus status) {}