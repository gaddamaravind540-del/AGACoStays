package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantOrderItemRepository extends JpaRepository<RestaurantOrderItem, Long> {
    java.util.List<RestaurantOrderItem> findByOrderId(Long orderId);
}
