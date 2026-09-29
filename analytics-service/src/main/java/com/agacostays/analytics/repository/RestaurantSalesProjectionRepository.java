package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.RestaurantSalesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface RestaurantSalesProjectionRepository extends JpaRepository<RestaurantSalesProjection,Long>{
    Optional<RestaurantSalesProjection> findByBranchIdAndMetricDate(Long branchId,LocalDate date);
    List<RestaurantSalesProjection> findByBranchIdAndMetricDateBetween(Long branchId,LocalDate from,LocalDate to);
    List<RestaurantSalesProjection> findByMetricDateBetween(LocalDate from,LocalDate to);
}
