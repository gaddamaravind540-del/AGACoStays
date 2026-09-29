package com.agacostays.room.util;

import com.agacostays.room.enums.RoomStatus;

public final class RoomAvailabilityUtil {
    private RoomAvailabilityUtil() {}

    public static boolean statusAllowsSearch(RoomStatus status) {
        return status == RoomStatus.AVAILABLE || status == RoomStatus.BOOKED;
    }
}
