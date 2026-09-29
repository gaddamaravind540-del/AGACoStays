package com.agacostays.attendance.event;
public record StaffCheckedOutEvent(Long attendanceId, Long staffId, Long branchId, java.time.LocalDate attendanceDate, java.time.LocalTime checkOutTime, java.math.BigDecimal workedHours){}
