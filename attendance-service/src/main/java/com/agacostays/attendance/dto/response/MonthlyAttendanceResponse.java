package com.agacostays.attendance.dto.response;
import java.math.BigDecimal;
import java.util.List;
public record MonthlyAttendanceResponse(
    Long staffId, Integer month, Integer year,
    long presentDays, long absentDays, long halfDays, long leaveDays,
    BigDecimal totalWorkedHours, List<AttendanceResponse> records
) {}
