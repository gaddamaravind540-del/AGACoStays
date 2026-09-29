package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantFeedbackRepository extends JpaRepository<RestaurantFeedback, Long> {
    java.util.List<RestaurantFeedback> findByRestaurantIdOrderByCreatedAtDesc(Long restaurantId);
}
