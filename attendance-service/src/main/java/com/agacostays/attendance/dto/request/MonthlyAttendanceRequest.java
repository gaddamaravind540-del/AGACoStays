package com.agacostays.attendance.dto.request;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
public record MonthlyAttendanceRequest(@NotNull @Min(1) @Max(12) Integer month, @NotNull Integer year) {}
