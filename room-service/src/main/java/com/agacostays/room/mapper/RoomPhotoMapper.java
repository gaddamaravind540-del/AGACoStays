package com.agacostays.room.mapper;

import com.agacostays.room.dto.response.RoomPhotoResponse;
import com.agacostays.room.entity.RoomPhoto;
import org.springframework.stereotype.Component;

@Component
public class RoomPhotoMapper {
    public RoomPhotoResponse toResponse(RoomPhoto photo) {
        return new RoomPhotoResponse(photo.getPhotoId(), photo.getBranchId(), photo.getRoomId(), photo.getPhotoUrl(),
                photo.getCaption(), photo.isPrimaryPhoto(), photo.getUploadedBy(), photo.getCreatedAt());
    }
}
