package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomPhotoDeletedEvent {
    private Long roomId;
    private  Long branchId;
    private  Long photoId;
    private  java.time.OffsetDateTime occurredAt;
}
