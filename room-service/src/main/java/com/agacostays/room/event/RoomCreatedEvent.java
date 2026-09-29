package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreatedEvent {
    private Long roomId;
    private  Long branchId;
    private  String roomNumber;
    private  java.time.OffsetDateTime occurredAt;
}
