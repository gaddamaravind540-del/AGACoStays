package com.agacostays.room.dto.response;

import java.time.OffsetDateTime;

public record RoomPhotoResponse(
        Long photoId,
        Long branchId,
        Long roomId,
        String photoUrl,
        String caption,
        boolean primary,
        Long uploadedBy,
        OffsetDateTime createdAt
) {}
