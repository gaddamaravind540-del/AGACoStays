package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {
    java.util.List<RestaurantMenu> findByBranchIdAndStatusNot(Long branchId, com.agacostays.restaurant.enums.MenuItemStatus status);
}
