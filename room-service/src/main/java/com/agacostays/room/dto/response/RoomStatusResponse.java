package com.agacostays.room.dto.response;

import com.agacostays.room.enums.RoomStatus;

public record RoomStatusResponse(Long roomId, Long branchId, RoomStatus status, String message) {}
