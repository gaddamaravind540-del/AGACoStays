package com.agacostays.restaurant.mapper;

import com.agacostays.restaurant.dto.response.DishPhotoResponse;
import com.agacostays.restaurant.entity.RestaurantMenuPhoto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMenuPhotoMapper {
    public DishPhotoResponse toResponse(RestaurantMenuPhoto p) {
        return DishPhotoResponse.builder()
                .photoId(p.getPhotoId()).menuItemId(p.getMenuItemId())
                .photoUrl(p.getPhotoUrl()).caption(p.getCaption()).primaryPhoto(p.isPrimaryPhoto()).build();
    }
}
