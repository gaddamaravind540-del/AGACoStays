package com.agacostays.room.dto.request;

import com.agacostays.room.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateRoomRequest(
        RoomType roomType,
        @DecimalMin(value = "0.01") BigDecimal basePricePerDay,
        String description,
        @PositiveOrZero Integer floor
) {}
