package com.agacostays.analytics.controller;

import com.agacostays.analytics.dto.response.ApiResponse;
import com.agacostays.analytics.dto.response.HotelDashboardResponse;
import com.agacostays.analytics.service.BranchAnalyticsService;
import com.agacostays.analytics.service.HotelAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotelAnalyticsController {

    private final HotelAnalyticsService hotel;
    private final BranchAnalyticsService branch;

    public HotelAnalyticsController(
            HotelAnalyticsService hotel,
            BranchAnalyticsService branch) {
        this.hotel = hotel;
        this.branch = branch;
    }

    @GetMapping("/api/analytics/hotel-dashboard")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> dashboard() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Hotel dashboard fetched",
                        hotel.dashboard(null)
                )
        );
    }

    @GetMapping("/api/analytics/revenue")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> revenue() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Revenue metrics fetched",
                        hotel.dashboard(null)
                )
        );
    }

    @GetMapping("/api/analytics/occupancy")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> occupancy() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Occupancy metrics fetched",
                        hotel.dashboard(null)
                )
        );
    }

    @GetMapping("/api/analytics/attendance")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> attendance() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Attendance metrics fetched",
                        hotel.dashboard(null)
                )
        );
    }

    @GetMapping("/api/analytics/payroll")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> payroll() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Payroll metrics fetched",
                        hotel.dashboard(null)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/dashboard")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branchDashboard(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch hotel dashboard fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/occupancy")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branchOccupancy(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch occupancy fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/bookings")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branchBookings(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch booking analytics fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/room-type-revenue")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> roomRevenue(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Room type revenue fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/customers")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> customers(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Customer analytics fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/housekeeping")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> housekeeping(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Housekeeping analytics fetched",
                        branch.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/hotel/attendance")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branchAttendance(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch attendance analytics fetched",
                        branch.branchHotel(branchId)
                )
        );
    }
}