package com.agacostays.room.service.impl;

import com.agacostays.room.dto.request.UpdateRoomStatusRequest;
import com.agacostays.room.dto.response.RoomStatusResponse;
import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.exception.ActiveBookingFoundException;
import com.agacostays.room.exception.RoomNotFoundException;
import com.agacostays.room.repository.RoomRepository;
import com.agacostays.room.security.BranchAccessValidator;
import com.agacostays.room.security.CurrentUserProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomStatusServiceImpl implements com.agacostays.room.service.RoomStatusService {
    private final RoomRepository roomRepository;
    private final BranchAccessValidator accessValidator;
    private final CurrentUserProvider currentUserProvider;

    public RoomStatusServiceImpl(RoomRepository roomRepository, BranchAccessValidator accessValidator,
                                 CurrentUserProvider currentUserProvider) {
        this.roomRepository = roomRepository;
        this.accessValidator = accessValidator;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public RoomStatusResponse updateStatus(Long branchId, Long roomId, UpdateRoomStatusRequest request) {
        accessValidator.validate(branchId);
        var room = roomRepository.findByRoomIdAndBranchId(roomId, branchId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        if (room.getStatus() == RoomStatus.BOOKED && request.status() == RoomStatus.MAINTENANCE) {
            throw new ActiveBookingFoundException("Booked room cannot be moved to maintenance");
        }
        room.setStatus(request.status());
        room.setUpdatedBy(currentUserProvider.getCurrentUser().userId());
        roomRepository.save(room);
        return new RoomStatusResponse(roomId, branchId, room.getStatus(), "Room status updated successfully");
    }
}
