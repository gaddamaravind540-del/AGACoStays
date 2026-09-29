package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.request.CreateFoodOrderRequest;
import com.agacostays.restaurant.dto.response.FoodOrderResponse;
import com.agacostays.restaurant.enums.FoodOrderStatus;
import java.util.List;

public interface FoodOrderService {
    FoodOrderResponse create(Long branchId, CreateFoodOrderRequest request);
    FoodOrderResponse get(Long orderId);
    List<FoodOrderResponse> myOrders();
    FoodOrderResponse updateStatus(Long orderId, FoodOrderStatus status);
}
