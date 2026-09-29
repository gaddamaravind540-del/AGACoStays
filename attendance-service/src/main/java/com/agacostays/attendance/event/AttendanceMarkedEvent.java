package com.agacostays.attendance.event;
public record AttendanceMarkedEvent(Long attendanceId, Long staffId, Long branchId, String status, java.time.LocalDate attendanceDate){}
