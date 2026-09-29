package com.agacostays.attendance.dto.response;
import java.time.LocalDate;
public record AbsentStaffResponse(Long staffId, Long branchId, LocalDate attendanceDate, String status) {}
