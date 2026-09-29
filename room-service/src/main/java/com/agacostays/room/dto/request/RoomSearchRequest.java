package com.agacostays.room.dto.request;

import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.enums.RoomType;

public record RoomSearchRequest(RoomType roomType, RoomStatus status, int page, int size) {
    public RoomSearchRequest {
        if (page < 0) page = 0;
        if (size < 1 || size > 100) size = 20;
    }
}
