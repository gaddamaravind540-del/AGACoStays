package com.agacostays.attendance.service;
import com.agacostays.attendance.dto.request.*;
import com.agacostays.attendance.dto.response.*;
import java.util.List;
public interface AttendanceService {
    CheckInResponse checkIn(Long staffId,CheckInRequest request);
    CheckOutResponse checkOut(Long staffId,CheckOutRequest request);
    AttendanceResponse mark(MarkAttendanceRequest request,Long actorId);
    AttendanceResponse correct(Long attendanceId,AttendanceCorrectionRequest request,Long actorId);
    List<AttendanceResponse> myAttendance(Long staffId);
    AttendanceResponse get(Long attendanceId);
    List<AttendanceResponse> staffAttendance(Long staffId);
    void delete(Long attendanceId);
}
