package com.agacostays.booking.controller;

import com.agacostays.booking.dto.request.CheckOutRequest;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.service.CheckOutService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class CheckOutController {

    private final CheckOutService service;

    public CheckOutController(CheckOutService service) {
        this.service = service;
    }

    @PostMapping("/{bookingId}/check-out")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<CheckOutResponse>> checkOut(
            @PathVariable Long bookingId,
            @RequestBody CheckOutRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Checkout completed", service.checkOut(bookingId, request)));
    }
}
