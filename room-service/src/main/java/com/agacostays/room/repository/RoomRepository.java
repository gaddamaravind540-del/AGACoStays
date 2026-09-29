package com.agacostays.room.repository;

import com.agacostays.room.entity.Room;
import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Page<Room> findByBranchIdAndStatusNot(Long branchId, RoomStatus status, Pageable pageable);
    Page<Room> findByBranchId(Long branchId, Pageable pageable);
    Optional<Room> findByRoomIdAndBranchId(Long roomId, Long branchId);
    boolean existsByBranchIdAndRoomNumber(Long branchId, String roomNumber);
    Page<Room> findByBranchIdAndRoomTypeAndStatus(Long branchId, RoomType roomType, RoomStatus status, Pageable pageable);
}
