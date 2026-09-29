package com.agacostays.room.controller;

import com.agacostays.room.dto.request.UpdateRoomStatusRequest;
import com.agacostays.room.dto.response.ApiResponse;
import com.agacostays.room.dto.response.RoomStatusResponse;
import com.agacostays.room.service.RoomStatusService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/hotel-branches/{branchId}/rooms")
public class RoomStatusController {
    private final RoomStatusService service;
    public RoomStatusController(RoomStatusService service) { this.service = service; }

    @PutMapping("/{roomId}/status")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST','HOUSEKEEPING_STAFF')")
    public ApiResponse<RoomStatusResponse> update(@PathVariable Long branchId, @PathVariable Long roomId,
                                                   @Valid @RequestBody UpdateRoomStatusRequest request) {
        return ApiResponse.success("Room status updated successfully", service.updateStatus(branchId, roomId, request));
    }
}
