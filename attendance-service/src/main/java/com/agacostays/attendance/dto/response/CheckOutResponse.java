package com.agacostays.attendance.dto.response;
import java.math.BigDecimal;
import java.time.LocalTime;
public record CheckOutResponse(Long attendanceId, Long staffId, LocalTime checkOutTime, BigDecimal workedHours, String status) {}
