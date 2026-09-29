package com.agacostays.restaurant.dto.response;

public record KitchenOrderResponse(Long orderId, com.agacostays.restaurant.enums.FoodOrderStatus status) {}
