package com.agacostays.support.repository;

import com.agacostays.support.entity.RestaurantFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RestaurantFeedbackRepository extends JpaRepository<RestaurantFeedback, Long> {
    List<RestaurantFeedback> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    List<RestaurantFeedback> findByBranchIdOrderByCreatedAtDesc(Long branchId);
    List<RestaurantFeedback> findByOrderIdOrderByCreatedAtDesc(Long orderId);
    Optional<RestaurantFeedback> findByOrderIdAndCustomerId(Long orderId, Long customerId);
}
