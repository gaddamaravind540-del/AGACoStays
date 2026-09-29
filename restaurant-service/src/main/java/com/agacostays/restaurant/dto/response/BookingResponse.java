package com.agacostays.restaurant.dto.response;

public record BookingResponse(Long bookingId, Long customerId, Long roomId, String status) {}
