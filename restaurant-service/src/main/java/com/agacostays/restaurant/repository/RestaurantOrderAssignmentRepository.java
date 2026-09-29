package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantOrderAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOrderAssignmentRepository extends JpaRepository<RestaurantOrderAssignment, Long> {
    java.util.Optional<RestaurantOrderAssignment> findByOrderId(Long orderId);
}
