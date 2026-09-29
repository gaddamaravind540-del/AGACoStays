package com.agacostays.attendance.validation;
import com.agacostays.attendance.entity.Attendance;
public interface AttendanceValidationService {
    void validateNewCheckIn(Long staffId,Long branchId);
    void validateCheckOut(Attendance attendance);
}
