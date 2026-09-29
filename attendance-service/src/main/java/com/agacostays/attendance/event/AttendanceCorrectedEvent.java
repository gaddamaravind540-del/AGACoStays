package com.agacostays.attendance.event;
public record AttendanceCorrectedEvent(Long attendanceId, Long staffId, Long branchId, java.time.LocalDate attendanceDate){}
