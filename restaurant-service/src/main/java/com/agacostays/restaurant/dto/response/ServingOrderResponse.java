package com.agacostays.restaurant.dto.response;

public record ServingOrderResponse(Long orderId, com.agacostays.restaurant.enums.FoodOrderStatus status) {}
