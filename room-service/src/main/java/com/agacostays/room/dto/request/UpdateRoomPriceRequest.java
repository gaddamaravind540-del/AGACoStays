package com.agacostays.room.dto.request;

import com.agacostays.room.enums.RoomPriceChangeReason;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateRoomPriceRequest(
        @NotNull @DecimalMin(value = "0.01") BigDecimal newPricePerDay,
        @NotNull LocalDate effectiveFrom,
        RoomPriceChangeReason reason
) {}
