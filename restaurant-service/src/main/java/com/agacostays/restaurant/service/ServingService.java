package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.response.ServingOrderResponse;

public interface ServingService {
    ServingOrderResponse pickedUp(Long orderId);
    ServingOrderResponse delivered(Long orderId);
}
