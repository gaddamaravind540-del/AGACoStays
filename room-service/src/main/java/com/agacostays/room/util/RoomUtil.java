package com.agacostays.room.util;

import com.agacostays.room.entity.Room;

public final class RoomUtil {
    private RoomUtil() {}

    public static boolean isBookable(Room room) {
        return room.getStatus() == com.agacostays.room.enums.RoomStatus.AVAILABLE;
    }
}
