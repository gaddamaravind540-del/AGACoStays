package com.agacostays.room.controller;

import com.agacostays.room.dto.request.RoomAvailabilityRequest;
import com.agacostays.room.dto.response.ApiResponse;
import com.agacostays.room.dto.response.RoomAvailabilityResponse;
import com.agacostays.room.service.RoomAvailabilityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/hotel-branches/{branchId}/rooms")
public class RoomAvailabilityController {
    private final RoomAvailabilityService service;
    public RoomAvailabilityController(RoomAvailabilityService service) { this.service = service; }

    @GetMapping("/availability")
    public ApiResponse<List<RoomAvailabilityResponse>> search(@PathVariable Long branchId,
                                                               @Valid @RequestParam LocalDate checkInDate,
                                                               @Valid @RequestParam LocalDate checkOutDate) {
        return ApiResponse.success("Room availability fetched successfully",
                service.search(branchId, new RoomAvailabilityRequest(checkInDate, checkOutDate)));
    }
}
