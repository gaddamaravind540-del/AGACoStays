package com.agacostays.room.mapper;

import com.agacostays.room.dto.response.RoomPriceHistoryResponse;
import com.agacostays.room.entity.RoomPriceHistory;
import org.springframework.stereotype.Component;

@Component
public class RoomPriceHistoryMapper {
    public RoomPriceHistoryResponse toResponse(RoomPriceHistory history) {
        return new RoomPriceHistoryResponse(history.getPriceHistoryId(), history.getRoomId(), history.getBranchId(),
                history.getOldPrice(), history.getNewPrice(), history.getChangedBy(), history.getChangedByRole(),
                history.getChangeReason(), history.getEffectiveFrom(), history.getCreatedAt());
    }
}
