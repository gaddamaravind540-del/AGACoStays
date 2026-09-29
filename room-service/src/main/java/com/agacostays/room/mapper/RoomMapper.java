package com.agacostays.room.mapper;

import com.agacostays.room.dto.response.RoomResponse;
import com.agacostays.room.dto.response.RoomSummaryResponse;
import com.agacostays.room.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public RoomResponse toResponse(Room room, String branchName, String primaryPhotoUrl) {
        return new RoomResponse(room.getRoomId(), room.getBranchId(), branchName, room.getRoomNumber(),
                room.getRoomType(), room.getBasePricePerDay(), room.getCurrentPricePerDay(), room.getDescription(),
                room.getFloor(), room.getStatus(), primaryPhotoUrl, room.getCreatedAt(), room.getUpdatedAt());
    }

    public RoomSummaryResponse toSummary(Room room, String primaryPhotoUrl) {
        return new RoomSummaryResponse(room.getRoomId(), room.getBranchId(), room.getRoomNumber(),
                room.getRoomType(), room.getCurrentPricePerDay(), room.getStatus(), primaryPhotoUrl);
    }
}
