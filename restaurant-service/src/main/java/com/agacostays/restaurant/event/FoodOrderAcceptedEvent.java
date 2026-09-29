package com.agacostays.restaurant.event;
public record FoodOrderAcceptedEvent(Long orderId, Long branchId, Long restaurantId, Long bookingId, Long customerId) {}
