package com.agacostays.room.repository;

import com.agacostays.room.entity.RoomPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomPriceHistoryRepository extends JpaRepository<RoomPriceHistory, Long> {
    List<RoomPriceHistory> findByBranchIdAndRoomIdOrderByEffectiveFromDescCreatedAtDesc(Long branchId, Long roomId);
}
