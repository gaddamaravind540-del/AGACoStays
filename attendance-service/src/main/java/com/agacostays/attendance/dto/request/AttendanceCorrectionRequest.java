package com.agacostays.attendance.dto.request;
import com.agacostays.attendance.enums.AttendanceStatus;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
public record AttendanceCorrectionRequest(
    AttendanceStatus requestedStatus, LocalTime requestedCheckIn,
    LocalTime requestedCheckOut, @NotBlank String reason
) {}
