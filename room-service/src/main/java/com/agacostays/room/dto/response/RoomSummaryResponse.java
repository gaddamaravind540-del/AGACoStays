package com.agacostays.room.dto.response;

import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.enums.RoomType;

import java.math.BigDecimal;

public record RoomSummaryResponse(
        Long roomId,
        Long branchId,
        String roomNumber,
        RoomType roomType,
        BigDecimal currentPricePerDay,
        RoomStatus status,
        String primaryPhotoUrl
) {}
