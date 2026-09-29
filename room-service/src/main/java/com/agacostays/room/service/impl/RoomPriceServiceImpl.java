package com.agacostays.room.service.impl;

import com.agacostays.room.dto.request.UpdateRoomPriceRequest;
import com.agacostays.room.dto.response.RoomPriceHistoryResponse;
import com.agacostays.room.dto.response.RoomResponse;
import com.agacostays.room.entity.RoomPriceHistory;
import com.agacostays.room.exception.RoomNotFoundException;
import com.agacostays.room.mapper.RoomPriceHistoryMapper;
import com.agacostays.room.repository.RoomPriceHistoryRepository;
import com.agacostays.room.repository.RoomRepository;
import com.agacostays.room.security.BranchAccessValidator;
import com.agacostays.room.security.CurrentUser;
import com.agacostays.room.security.CurrentUserProvider;
import com.agacostays.room.util.RoomPriceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomPriceServiceImpl implements com.agacostays.room.service.RoomPriceService {

    private final RoomRepository roomRepository;
    private final RoomPriceHistoryRepository historyRepository;
    private final RoomPriceHistoryMapper historyMapper;
    private final BranchAccessValidator accessValidator;
    private final CurrentUserProvider currentUserProvider;
    private final com.agacostays.room.service.RoomService roomService;

    public RoomPriceServiceImpl(RoomRepository roomRepository, RoomPriceHistoryRepository historyRepository,
                                RoomPriceHistoryMapper historyMapper, BranchAccessValidator accessValidator,
                                CurrentUserProvider currentUserProvider, com.agacostays.room.service.RoomService roomService) {
        this.roomRepository = roomRepository;
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
        this.accessValidator = accessValidator;
        this.currentUserProvider = currentUserProvider;
        this.roomService = roomService;
    }

    @Override
    public RoomResponse updatePrice(Long branchId, Long roomId, UpdateRoomPriceRequest request) {
        accessValidator.validate(branchId);
        RoomPriceUtil.validate(request.newPricePerDay());
        var room = roomRepository.findByRoomIdAndBranchId(roomId, branchId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
        CurrentUser user = currentUserProvider.getCurrentUser();
        RoomPriceHistory history = RoomPriceHistory.builder()
                .roomId(roomId).branchId(branchId).oldPrice(room.getCurrentPricePerDay()).newPrice(request.newPricePerDay())
                .changedBy(user.userId()).changedByRole(user.role())
                .changeReason(request.reason() == null ? "MANUAL" : request.reason().name())
                .effectiveFrom(request.effectiveFrom()).build();
        historyRepository.save(history);
        room.setCurrentPricePerDay(request.newPricePerDay());
        room.setUpdatedBy(user.userId());
        roomRepository.save(room);
        return roomService.get(branchId, roomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomPriceHistoryResponse> history(Long branchId, Long roomId) {
        accessValidator.validate(branchId);
        if (!roomRepository.existsById(roomId)) throw new RoomNotFoundException("Room not found");
        return historyRepository.findByBranchIdAndRoomIdOrderByEffectiveFromDescCreatedAtDesc(branchId, roomId)
                .stream().map(historyMapper::toResponse).toList();
    }
}
