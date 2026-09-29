package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomPhotoUploadedEvent {
    private Long roomId;
    private  Long branchId;
    private  Long photoId;
    private  String photoUrl;
    private  java.time.OffsetDateTime occurredAt;
}
