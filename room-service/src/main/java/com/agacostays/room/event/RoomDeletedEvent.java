package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDeletedEvent {
    private Long roomId;
    private  Long branchId;
    private  java.time.OffsetDateTime occurredAt;
}
