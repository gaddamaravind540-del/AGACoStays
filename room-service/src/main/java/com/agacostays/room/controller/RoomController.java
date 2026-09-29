package com.agacostays.room.controller;

import com.agacostays.room.dto.request.CreateRoomRequest;
import com.agacostays.room.dto.request.RoomSearchRequest;
import com.agacostays.room.dto.request.UpdateRoomRequest;
import com.agacostays.room.dto.response.ApiResponse;
import com.agacostays.room.dto.response.PageResponse;
import com.agacostays.room.dto.response.RoomResponse;
import com.agacostays.room.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) { this.roomService = roomService; }

    @PostMapping("/manager/hotel-branches/{branchId}/rooms")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<RoomResponse> create(@PathVariable Long branchId, @Valid @RequestBody CreateRoomRequest request) {
        return ApiResponse.success("Room created successfully", roomService.create(branchId, request));
    }

    @GetMapping("/hotel-branches/{branchId}/rooms")
    public ApiResponse<PageResponse<RoomResponse>> list(@PathVariable Long branchId,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size,
                                                         @RequestParam(required = false) com.agacostays.room.enums.RoomType roomType,
                                                         @RequestParam(required = false) com.agacostays.room.enums.RoomStatus status) {
        return ApiResponse.success("Rooms fetched successfully", roomService.list(branchId, new RoomSearchRequest(roomType, status, page, size)));
    }

    @GetMapping("/hotel-branches/{branchId}/rooms/{roomId}")
    public ApiResponse<RoomResponse> get(@PathVariable Long branchId, @PathVariable Long roomId) {
        return ApiResponse.success("Room fetched successfully", roomService.get(branchId, roomId));
    }

    @PutMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ApiResponse<RoomResponse> update(@PathVariable Long branchId, @PathVariable Long roomId,
                                             @Valid @RequestBody UpdateRoomRequest request) {
        return ApiResponse.success("Room updated successfully", roomService.update(branchId, roomId, request));
    }

    @DeleteMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}")
    @PreAuthorize("hasRole('MANAGER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long branchId, @PathVariable Long roomId) {
        roomService.softDelete(branchId, roomId);
    }
}
