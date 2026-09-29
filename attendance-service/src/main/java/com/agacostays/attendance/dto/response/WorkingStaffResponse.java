package com.agacostays.attendance.dto.response;
import java.time.LocalTime;
public record WorkingStaffResponse(Long attendanceId, Long staffId, Long branchId, LocalTime checkInTime) {}
