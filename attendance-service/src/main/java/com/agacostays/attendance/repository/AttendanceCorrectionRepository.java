package com.agacostays.attendance.repository;
import com.agacostays.attendance.entity.AttendanceCorrection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface AttendanceCorrectionRepository extends JpaRepository<AttendanceCorrection,Long> {
    List<AttendanceCorrection> findByAttendanceIdOrderByCreatedAtDesc(Long attendanceId);
}
