package com.agacostays.support.dto.response;

public record RestaurantOrderResponse(Long orderId, Long branchId, Long bookingId, Long customerId, String status) {}
