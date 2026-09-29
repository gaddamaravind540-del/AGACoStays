package com.agacostays.room.dto.request;

import com.agacostays.room.enums.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record CreateRoomRequest(
        @NotBlank(message = "roomNumber is required") String roomNumber,
        @NotNull(message = "roomType is required") RoomType roomType,
        @NotNull(message = "basePricePerDay is required") @DecimalMin(value = "0.01") BigDecimal basePricePerDay,
        String description,
        @PositiveOrZero Integer floor
) {}
