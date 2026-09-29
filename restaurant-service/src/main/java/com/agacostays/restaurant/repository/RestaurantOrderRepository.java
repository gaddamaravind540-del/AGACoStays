package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Long> {
    java.util.List<RestaurantOrder> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    java.util.List<RestaurantOrder> findByBranchIdOrderByCreatedAtDesc(Long branchId);
}
