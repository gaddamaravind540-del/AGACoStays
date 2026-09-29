package com.agacostays.room.service.impl;

import com.agacostays.room.client.BranchServiceClient;
import com.agacostays.room.dto.request.CreateRoomRequest;
import com.agacostays.room.dto.request.RoomSearchRequest;
import com.agacostays.room.dto.request.UpdateRoomRequest;
import com.agacostays.room.dto.response.PageResponse;
import com.agacostays.room.dto.response.RoomResponse;
import com.agacostays.room.entity.Room;
import com.agacostays.room.enums.RoomStatus;
import com.agacostays.room.exception.RoomAlreadyExistsException;
import com.agacostays.room.exception.RoomNotFoundException;
import com.agacostays.room.mapper.RoomMapper;
import com.agacostays.room.repository.RoomPhotoRepository;
import com.agacostays.room.repository.RoomRepository;
import com.agacostays.room.security.BranchAccessValidator;
import com.agacostays.room.security.CurrentUser;
import com.agacostays.room.security.CurrentUserProvider;
import com.agacostays.room.validation.RoomValidationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomServiceImpl implements com.agacostays.room.service.RoomService {

    private final RoomRepository roomRepository;
    private final RoomPhotoRepository roomPhotoRepository;
    private final RoomMapper roomMapper;
    private final BranchServiceClient branchServiceClient;
    private final BranchAccessValidator branchAccessValidator;
    private final CurrentUserProvider currentUserProvider;
    private final RoomValidationService roomValidationService;

    public RoomServiceImpl(RoomRepository roomRepository, RoomPhotoRepository roomPhotoRepository, RoomMapper roomMapper,
                           BranchServiceClient branchServiceClient, BranchAccessValidator branchAccessValidator,
                           CurrentUserProvider currentUserProvider, RoomValidationService roomValidationService) {
        this.roomRepository = roomRepository;
        this.roomPhotoRepository = roomPhotoRepository;
        this.roomMapper = roomMapper;
        this.branchServiceClient = branchServiceClient;
        this.branchAccessValidator = branchAccessValidator;
        this.currentUserProvider = currentUserProvider;
        this.roomValidationService = roomValidationService;
    }

    @Override
    public RoomResponse create(Long branchId, CreateRoomRequest request) {
        branchAccessValidator.validate(branchId);
        roomValidationService.validateRoomForWrite();
        validateBranchExists(branchId);
        if (roomRepository.existsByBranchIdAndRoomNumber(branchId, request.roomNumber())) {
            throw new RoomAlreadyExistsException("Room " + request.roomNumber() + " already exists in branch " + branchId);
        }
        CurrentUser user = currentUserProvider.getCurrentUser();
        Room room = Room.builder()
                .branchId(branchId)
                .roomNumber(request.roomNumber().trim())
                .roomType(request.roomType())
                .basePricePerDay(request.basePricePerDay())
                .currentPricePerDay(request.basePricePerDay())
                .description(request.description())
                .floor(request.floor())
                .status(RoomStatus.AVAILABLE)
                .createdBy(user.userId())
                .updatedBy(user.userId())
                .build();
        Room saved = roomRepository.save(room);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RoomResponse> list(Long branchId, RoomSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.page(), request.size(), Sort.by("roomNumber").ascending());
        Page<Room> page;
        if (request.roomType() != null && request.status() != null) {
            page = roomRepository.findByBranchIdAndRoomTypeAndStatus(branchId, request.roomType(), request.status(), pageRequest);
        } else {
            page = roomRepository.findByBranchIdAndStatusNot(branchId, RoomStatus.MAINTENANCE, pageRequest);
        }
        return PageResponse.from(page.map(this::toResponse));
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse get(Long branchId, Long roomId) {
        return toResponse(findRoom(branchId, roomId));
    }

    @Override
    public RoomResponse update(Long branchId, Long roomId, UpdateRoomRequest request) {
        branchAccessValidator.validate(branchId);
        Room room = findRoom(branchId, roomId);
        if (request.roomType() != null) room.setRoomType(request.roomType());
        if (request.basePricePerDay() != null) room.setBasePricePerDay(request.basePricePerDay());
        if (request.description() != null) room.setDescription(request.description());
        if (request.floor() != null) room.setFloor(request.floor());
        CurrentUser user = currentUserProvider.getCurrentUser();
        room.setUpdatedBy(user.userId());
        return toResponse(roomRepository.save(room));
    }

    @Override
    public void softDelete(Long branchId, Long roomId) {
        branchAccessValidator.validate(branchId);
        Room room = findRoom(branchId, roomId);
        room.setStatus(RoomStatus.MAINTENANCE);
        room.setUpdatedBy(currentUserProvider.getCurrentUser().userId());
        roomRepository.save(room);
    }

    private Room findRoom(Long branchId, Long roomId) {
        return roomRepository.findByRoomIdAndBranchId(roomId, branchId)
                .orElseThrow(() -> new RoomNotFoundException("Room " + roomId + " not found in branch " + branchId));
    }

    private RoomResponse toResponse(Room room) {
        String branchName = null;
        try {
            var branch = branchServiceClient.getBranch(room.getBranchId());
            branchName = branch == null ? null : branch.branchName();
        } catch (Exception ignored) {
            // Keep room response available while another service is temporarily unavailable.
        }
        String primaryPhoto = roomPhotoRepository.findByBranchIdAndRoomIdOrderByPrimaryPhotoDescCreatedAtAsc(room.getBranchId(), room.getRoomId())
                .stream().findFirst().map(p -> p.getPhotoUrl()).orElse(null);
        return roomMapper.toResponse(room, branchName, primaryPhoto);
    }

    private void validateBranchExists(Long branchId) {
        try {
            if (branchServiceClient.getBranch(branchId) == null) {
                throw new com.agacostays.room.exception.BranchNotFoundException("Branch " + branchId + " not found");
            }
        } catch (com.agacostays.room.exception.BranchNotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new com.agacostays.room.exception.BranchNotFoundException("Could not validate branch " + branchId);
        }
    }
}
