package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.FeedbackProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface FeedbackProjectionRepository extends JpaRepository<FeedbackProjection,Long>{
    Optional<FeedbackProjection> findByBranchIdAndMetricDate(Long branchId,LocalDate date);
    List<FeedbackProjection> findByBranchIdAndMetricDateBetween(Long branchId,LocalDate from,LocalDate to);
    List<FeedbackProjection> findByMetricDateBetween(LocalDate from,LocalDate to);
}
