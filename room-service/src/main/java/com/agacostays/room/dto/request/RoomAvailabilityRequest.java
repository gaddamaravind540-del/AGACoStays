package com.agacostays.room.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RoomAvailabilityRequest(
        @NotNull LocalDate checkInDate,
        @NotNull LocalDate checkOutDate
) {}
