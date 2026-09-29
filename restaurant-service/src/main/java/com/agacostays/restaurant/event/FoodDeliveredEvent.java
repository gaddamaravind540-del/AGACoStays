package com.agacostays.restaurant.event;
public record FoodDeliveredEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
