package com.agacostays.room.dto.response;

public record BookingStatusResponse(Long roomId, boolean bookingExists, String message) {}
