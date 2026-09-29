package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.PayrollProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface PayrollProjectionRepository extends JpaRepository<PayrollProjection,Long>{
    Optional<PayrollProjection> findByBranchIdAndMetricDate(Long branchId,LocalDate date);
    List<PayrollProjection> findByBranchIdAndMetricDateBetween(Long branchId,LocalDate from,LocalDate to);
    List<PayrollProjection> findByMetricDateBetween(LocalDate from,LocalDate to);
}
