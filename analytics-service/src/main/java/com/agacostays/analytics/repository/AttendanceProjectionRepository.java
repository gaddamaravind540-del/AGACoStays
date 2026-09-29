package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.AttendanceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface AttendanceProjectionRepository extends JpaRepository<AttendanceProjection,Long>{
    Optional<AttendanceProjection> findByBranchIdAndMetricDate(Long branchId,LocalDate date);
    List<AttendanceProjection> findByBranchIdAndMetricDateBetween(Long branchId,LocalDate from,LocalDate to);
    List<AttendanceProjection> findByMetricDateBetween(LocalDate from,LocalDate to);
}
