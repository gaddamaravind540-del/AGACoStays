package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomStatusChangedEvent {
    private Long roomId;
    private  Long branchId;
    private  String oldStatus;
    private  String newStatus;
    private  java.time.OffsetDateTime occurredAt;
}
