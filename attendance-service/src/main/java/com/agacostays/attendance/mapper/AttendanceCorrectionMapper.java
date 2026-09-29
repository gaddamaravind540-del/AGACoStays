package com.agacostays.attendance.mapper;
import com.agacostays.attendance.entity.AttendanceCorrection;
import org.springframework.stereotype.Component;
@Component
public class AttendanceCorrectionMapper {
    public AttendanceCorrectionMapper() {}
    public AttendanceCorrection copy(AttendanceCorrection x){ return x; }
}
