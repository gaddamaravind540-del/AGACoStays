package com.agacostays.booking.controller;

import com.agacostays.booking.dto.request.BookingStatusRequest;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingStatusController {

    private final BookingService bookingService;

    public BookingStatusController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PutMapping("/{bookingId}/status")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> status(
            @PathVariable Long bookingId,
            @RequestBody BookingStatusRequest request) {

        return switch (request.getStatus()) {
            case APPROVED -> ResponseEntity.ok(ApiResponse.success(
                    "Booking approved", bookingService.approve(bookingId, request.getRemarks())));
            case REJECTED -> ResponseEntity.ok(ApiResponse.success(
                    "Booking rejected", bookingService.reject(bookingId, request.getRemarks())));
            default -> throw new IllegalArgumentException("Use dedicated API for this booking state");
        };
    }
}
