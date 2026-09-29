package com.agacostays.room.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record RoomPriceHistoryResponse(
        Long priceHistoryId,
        Long roomId,
        Long branchId,
        BigDecimal oldPrice,
        BigDecimal newPrice,
        Long changedBy,
        String changedByRole,
        String changeReason,
        LocalDate effectiveFrom,
        OffsetDateTime createdAt
) {}
