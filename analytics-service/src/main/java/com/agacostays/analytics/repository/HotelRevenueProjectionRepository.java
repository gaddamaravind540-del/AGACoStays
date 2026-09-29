package com.agacostays.analytics.repository;
import com.agacostays.analytics.entity.HotelRevenueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface HotelRevenueProjectionRepository extends JpaRepository<HotelRevenueProjection,Long>{
    Optional<HotelRevenueProjection> findByBranchIdAndMetricDate(Long branchId,LocalDate date);
    List<HotelRevenueProjection> findByBranchIdAndMetricDateBetween(Long branchId,LocalDate from,LocalDate to);
    List<HotelRevenueProjection> findByMetricDateBetween(LocalDate from,LocalDate to);
}
