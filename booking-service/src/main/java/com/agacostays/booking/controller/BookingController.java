package com.agacostays.booking.controller;

import com.agacostays.booking.constants.HeaderConstants;
import com.agacostays.booking.dto.request.*;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> get(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.success("Booking fetched successfully", bookingService.getById(bookingId)));
    }

    @GetMapping("/bookings/my-bookings")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<java.util.List<BookingResponse>>> myBookings() {
        return ResponseEntity.ok(ApiResponse.success("Bookings fetched successfully", bookingService.getMyBookings()));
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<ApiResponse<BookingResponse>> update(
            @PathVariable Long bookingId,
            @Valid @RequestBody UpdateBookingRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Booking updated successfully", bookingService.update(bookingId, request)));
    }

    @PutMapping("/bookings/{bookingId}/approve")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> approve(
            @PathVariable Long bookingId,
            @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(ApiResponse.success("Booking approved", bookingService.approve(bookingId, remarks)));
    }

    @PutMapping("/bookings/{bookingId}/reject")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> reject(
            @PathVariable Long bookingId,
            @RequestParam(required = false) String remarks) {
        return ResponseEntity.ok(ApiResponse.success("Booking rejected", bookingService.reject(bookingId, remarks)));
    }

    @PutMapping("/bookings/{bookingId}/cancel")
    public ResponseEntity<ApiResponse<BookingStatusResponse>> cancel(
            @PathVariable Long bookingId,
            @RequestBody BookingCancelRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Booking cancelled", bookingService.cancel(bookingId, request)));
    }
    @GetMapping("/bookings/{bookingId}/history")
    public ResponseEntity<ApiResponse<java.util.List<BookingHistoryResponse>>> history(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.success("Booking history fetched", bookingService.history(bookingId)));
    }

    @GetMapping("/hotel-branches/{branchId}/bookings")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<PageResponse<BookingResponse>>> branchBookings(
            @PathVariable Long branchId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(
                "Branch bookings fetched", bookingService.getBranchBookings(branchId, pageable)));
    }

    @GetMapping("/hotel-branches/{branchId}/bookings/date-range")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<java.util.List<BookingResponse>>> dateRange(
            @PathVariable Long branchId,
            @Valid BookingDateRangeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
                "Bookings fetched", bookingService.searchByDateRange(branchId, request)));
    }
}
