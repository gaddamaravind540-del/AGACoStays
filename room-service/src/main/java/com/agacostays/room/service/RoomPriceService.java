package com.agacostays.room.service;

import com.agacostays.room.dto.request.UpdateRoomPriceRequest;
import com.agacostays.room.dto.response.RoomPriceHistoryResponse;
import com.agacostays.room.dto.response.RoomResponse;

import java.util.List;

public interface RoomPriceService {
    RoomResponse updatePrice(Long branchId, Long roomId, UpdateRoomPriceRequest request);
    List<RoomPriceHistoryResponse> history(Long branchId, Long roomId);
}
