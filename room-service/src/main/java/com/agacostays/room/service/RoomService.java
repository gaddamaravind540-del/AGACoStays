package com.agacostays.room.service;

import com.agacostays.room.dto.request.CreateRoomRequest;
import com.agacostays.room.dto.request.RoomSearchRequest;
import com.agacostays.room.dto.request.UpdateRoomRequest;
import com.agacostays.room.dto.response.PageResponse;
import com.agacostays.room.dto.response.RoomResponse;

public interface RoomService {
    RoomResponse create(Long branchId, CreateRoomRequest request);
    PageResponse<RoomResponse> list(Long branchId, RoomSearchRequest request);
    RoomResponse get(Long branchId, Long roomId);
    RoomResponse update(Long branchId, Long roomId, UpdateRoomRequest request);
    void softDelete(Long branchId, Long roomId);
}
