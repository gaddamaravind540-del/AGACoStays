package com.agacostays.attendance.dto.request;
import java.time.LocalDate;
public record AttendanceSearchRequest(Long staffId, Long branchId, LocalDate fromDate, LocalDate toDate) {}
