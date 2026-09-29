package com.agacostays.restaurant.repository;

import com.agacostays.restaurant.entity.RestaurantMenuPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantMenuPhotoRepository extends JpaRepository<RestaurantMenuPhoto, Long> {
    java.util.List<RestaurantMenuPhoto> findByMenuItemId(Long menuItemId);
}
