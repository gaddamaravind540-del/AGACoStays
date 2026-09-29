package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.response.KitchenOrderResponse;

public interface KitchenService {
    KitchenOrderResponse preparing(Long orderId);
    KitchenOrderResponse ready(Long orderId);
}
