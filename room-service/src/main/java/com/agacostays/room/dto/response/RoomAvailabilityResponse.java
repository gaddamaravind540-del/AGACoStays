package com.agacostays.room.dto.response;

import com.agacostays.room.enums.RoomAvailabilityStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RoomAvailabilityResponse(
        Long roomId,
        Long branchId,
        String roomNumber,
        RoomAvailabilityStatus availabilityStatus,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal currentPricePerDay,
        String reason
) {}
