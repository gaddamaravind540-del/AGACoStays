package com.agacostays.restaurant.event;
public record FoodOrderCancelledEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
