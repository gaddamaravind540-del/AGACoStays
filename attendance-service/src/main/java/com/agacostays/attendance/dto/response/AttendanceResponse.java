package com.agacostays.attendance.dto.response;
import java.math.BigDecimal;
import java.time.*;
public record AttendanceResponse(
    Long attendanceId, Long branchId, Long staffId, LocalDate attendanceDate,
    String status, String source, String leaveType,
    LocalTime checkInTime, LocalTime checkOutTime, BigDecimal workedHours,
    String remarks, Long createdBy, Long updatedBy, OffsetDateTime createdAt, OffsetDateTime updatedAt
) {}
