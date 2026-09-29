package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.request.DishPhotoRequest;
import com.agacostays.restaurant.dto.response.DishPhotoResponse;
import java.util.List;

public interface DishPhotoService {
    DishPhotoResponse add(Long branchId, Long menuItemId, DishPhotoRequest request);
    List<DishPhotoResponse> list(Long menuItemId);
    void delete(Long photoId);
}
