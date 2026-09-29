package com.agacostays.restaurant.event;
public record FoodOrderRejectedEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
