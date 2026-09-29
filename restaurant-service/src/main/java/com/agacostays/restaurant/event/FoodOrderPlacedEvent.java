package com.agacostays.restaurant.event;
public record FoodOrderPlacedEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
