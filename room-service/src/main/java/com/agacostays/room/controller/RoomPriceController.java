package com.agacostays.room.controller;

import com.agacostays.room.dto.request.UpdateRoomPriceRequest;
import com.agacostays.room.dto.response.ApiResponse;
import com.agacostays.room.dto.response.RoomPriceHistoryResponse;
import com.agacostays.room.dto.response.RoomResponse;
import com.agacostays.room.service.RoomPriceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomPriceController {
    private final RoomPriceService service;
    public RoomPriceController(RoomPriceService service) { this.service = service; }

    @PutMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}/price")
    @PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
    public ApiResponse<RoomResponse> updatePrice(@PathVariable Long branchId, @PathVariable Long roomId,
                                                  @Valid @RequestBody UpdateRoomPriceRequest request) {
        return ApiResponse.success("Room price updated successfully", service.updatePrice(branchId, roomId, request));
    }

    @GetMapping("/manager/hotel-branches/{branchId}/rooms/{roomId}/price-history")
    @PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
    public ApiResponse<List<RoomPriceHistoryResponse>> history(@PathVariable Long branchId, @PathVariable Long roomId) {
        return ApiResponse.success("Room price history fetched successfully", service.history(branchId, roomId));
    }
}
