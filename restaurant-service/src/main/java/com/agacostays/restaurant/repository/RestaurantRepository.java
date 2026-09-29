package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    java.util.Optional<Restaurant> findByBranchId(Long branchId);
}
