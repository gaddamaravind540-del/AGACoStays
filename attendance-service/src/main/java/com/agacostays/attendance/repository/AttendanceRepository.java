package com.agacostays.attendance.repository;
import com.agacostays.attendance.entity.Attendance;
import com.agacostays.attendance.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {
    Optional<Attendance> findByStaffIdAndAttendanceDate(Long staffId, LocalDate date);
    List<Attendance> findByStaffIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(Long staffId, LocalDate from, LocalDate to);
    List<Attendance> findByBranchIdAndAttendanceDateOrderByStaffIdAsc(Long branchId, LocalDate date);
    List<Attendance> findByBranchIdAndAttendanceDateAndStatus(Long branchId, LocalDate date, AttendanceStatus status);
    List<Attendance> findByStaffIdAndAttendanceDateBetween(Long staffId, LocalDate from, LocalDate to);
}
