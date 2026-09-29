package com.agacostays.room.service;

import com.agacostays.room.dto.request.RoomAvailabilityRequest;
import com.agacostays.room.dto.response.RoomAvailabilityResponse;

import java.util.List;

public interface RoomAvailabilityService {
    List<RoomAvailabilityResponse> search(Long branchId, RoomAvailabilityRequest request);
}
