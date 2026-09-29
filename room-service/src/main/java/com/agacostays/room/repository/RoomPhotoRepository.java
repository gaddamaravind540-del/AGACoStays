package com.agacostays.room.repository;

import com.agacostays.room.entity.RoomPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomPhotoRepository extends JpaRepository<RoomPhoto, Long> {
    List<RoomPhoto> findByBranchIdAndRoomIdOrderByPrimaryPhotoDescCreatedAtAsc(Long branchId, Long roomId);
    Optional<RoomPhoto> findByPhotoIdAndBranchIdAndRoomId(Long photoId, Long branchId, Long roomId);
    long countByBranchIdAndRoomId(Long branchId, Long roomId);
}
