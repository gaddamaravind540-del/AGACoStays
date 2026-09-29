package com.agacostays.restaurant.event;
public record FoodReadyEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
