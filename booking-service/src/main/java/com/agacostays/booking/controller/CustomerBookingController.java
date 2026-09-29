package com.agacostays.booking.controller;

import com.agacostays.booking.constants.HeaderConstants;
import com.agacostays.booking.dto.request.CreateBookingRequest;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel-branches/{branchId}/bookings")
public class CustomerBookingController {

    private final BookingService bookingService;

    public CustomerBookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<BookingResponse>> create(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateBookingRequest request,
            @RequestHeader(value = HeaderConstants.IDEMPOTENCY_KEY, required = false) String idempotencyKey) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking created successfully",
                        bookingService.create(branchId, request, idempotencyKey)));
    }

    @PostMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BookingResponse>> createForCustomer(
            @PathVariable Long branchId,
            @PathVariable Long customerId,
            @Valid @RequestBody CreateBookingRequest request,
            @RequestHeader(value = HeaderConstants.IDEMPOTENCY_KEY, required = false) String idempotencyKey) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Booking created for customer",
                        bookingService.createForCustomer(branchId, customerId, request, idempotencyKey)));
    }
}
