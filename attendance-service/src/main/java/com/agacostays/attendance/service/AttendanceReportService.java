package com.agacostays.attendance.service;
import com.agacostays.attendance.dto.response.*;
public interface AttendanceReportService {
    MonthlyAttendanceResponse monthly(Long staffId,int month,int year);
    PageResponse<AttendanceResponse> branch(Long branchId,int page,int size);
}
