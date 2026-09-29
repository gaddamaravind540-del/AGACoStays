package com.agacostays.room.dto.request;

import com.agacostays.room.enums.RoomStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateRoomStatusRequest(@NotNull RoomStatus status) {}
