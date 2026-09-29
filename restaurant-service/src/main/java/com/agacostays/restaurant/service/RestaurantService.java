package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.request.RestaurantRequest;
import com.agacostays.restaurant.dto.response.RestaurantResponse;

public interface RestaurantService {
    RestaurantResponse get(Long branchId);
    RestaurantResponse create(Long branchId, RestaurantRequest request);
    RestaurantResponse update(Long branchId, RestaurantRequest request);
}
