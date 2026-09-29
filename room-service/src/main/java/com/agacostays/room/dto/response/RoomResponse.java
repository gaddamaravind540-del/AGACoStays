package com.agacostays.room.dto.response;

import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.enums.RoomType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record RoomResponse(
        Long roomId,
        Long branchId,
        String branchName,
        String roomNumber,
        RoomType roomType,
        BigDecimal basePricePerDay,
        BigDecimal currentPricePerDay,
        String description,
        Integer floor,
        RoomStatus status,
        String primaryPhotoUrl,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
