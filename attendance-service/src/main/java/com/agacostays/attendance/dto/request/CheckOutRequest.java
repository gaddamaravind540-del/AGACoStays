package com.agacostays.attendance.dto.request;
import jakarta.validation.constraints.NotNull;
public record CheckOutRequest(@NotNull Long branchId, String deviceId, Double latitude, Double longitude) {}
