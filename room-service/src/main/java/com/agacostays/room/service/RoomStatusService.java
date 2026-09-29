package com.agacostays.room.service;

import com.agacostays.room.dto.request.UpdateRoomStatusRequest;
import com.agacostays.room.dto.response.RoomStatusResponse;

public interface RoomStatusService {
    RoomStatusResponse updateStatus(Long branchId, Long roomId, UpdateRoomStatusRequest request);
}
