package com.agacostays.attendance.mapper;
import com.agacostays.attendance.dto.response.AttendanceResponse;
import com.agacostays.attendance.entity.Attendance;
import org.springframework.stereotype.Component;
@Component
public class AttendanceMapper {
    public AttendanceResponse toResponse(Attendance a) {
        return new AttendanceResponse(a.getAttendanceId(),a.getBranchId(),a.getStaffId(),a.getAttendanceDate(),
            a.getStatus().name(),a.getSource().name(),a.getLeaveType().name(),a.getCheckInTime(),a.getCheckOutTime(),
            a.getWorkedHours(),a.getRemarks(),a.getCreatedBy(),a.getUpdatedBy(),a.getCreatedAt(),a.getUpdatedAt());
    }
}
