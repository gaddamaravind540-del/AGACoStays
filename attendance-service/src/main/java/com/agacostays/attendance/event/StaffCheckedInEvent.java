package com.agacostays.attendance.event;
public record StaffCheckedInEvent(Long attendanceId, Long staffId, Long branchId, java.time.LocalDate attendanceDate, java.time.LocalTime checkInTime){}
