package com.agacostays.booking.controller;

import com.agacostays.booking.dto.request.CheckInRequest;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.service.CheckInService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class CheckInController {

    private final CheckInService service;

    public CheckInController(CheckInService service) {
        this.service = service;
    }

    @PostMapping("/{bookingId}/check-in")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<CheckInResponse>> checkIn(
            @PathVariable Long bookingId,
            @Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Check-in completed", service.checkIn(bookingId, request)));
    }
}
