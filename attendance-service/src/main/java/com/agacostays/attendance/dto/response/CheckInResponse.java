package com.agacostays.attendance.dto.response;
import java.time.LocalTime;
public record CheckInResponse(Long attendanceId, Long staffId, Long branchId, LocalTime checkInTime, String status) {}
