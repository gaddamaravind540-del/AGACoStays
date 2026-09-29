package com.agacostays.room.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomPriceUpdatedEvent {
    private Long roomId;
    private  Long branchId;
    private  java.math.BigDecimal oldPrice;
    private  java.math.BigDecimal newPrice;
    private  java.time.OffsetDateTime occurredAt;
}
