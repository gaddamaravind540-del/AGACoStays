package com.agacostays.attendance.dto.request;
import com.agacostays.attendance.enums.AttendanceStatus;
import com.agacostays.attendance.enums.LeaveType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
public record MarkAttendanceRequest(
    @NotNull Long branchId, @NotNull Long staffId, @NotNull LocalDate attendanceDate,
    @NotNull AttendanceStatus status, LocalTime checkInTime, LocalTime checkOutTime,
    LeaveType leaveType, String remarks
) {}
