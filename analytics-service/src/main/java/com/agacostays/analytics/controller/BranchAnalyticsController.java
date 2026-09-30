package com.agacostays.analytics.controller;

import com.agacostays.analytics.dto.response.ApiResponse;
import com.agacostays.analytics.dto.response.BranchRevenueResponse;
import com.agacostays.analytics.dto.response.HotelDashboardResponse;
import com.agacostays.analytics.dto.response.ManagerDashboardResponse;
import com.agacostays.analytics.dto.response.RootDashboardResponse;
import com.agacostays.analytics.service.BranchAnalyticsService;
import com.agacostays.analytics.service.HotelAnalyticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class BranchAnalyticsController {

    private final BranchAnalyticsService service;
    private final HotelAnalyticsService hotel;

    public BranchAnalyticsController(
            BranchAnalyticsService service,
            HotelAnalyticsService hotel) {
        this.service = service;
        this.hotel = hotel;
    }

    @GetMapping("/api/analytics/hotel-branches/{branchId}")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branch(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch analytics fetched",
                        service.branchHotel(branchId)
                )
        );
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/overall/revenue")
    public ResponseEntity<ApiResponse<BranchRevenueResponse>> overall(
            @PathVariable Long branchId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Branch overall revenue fetched",
                        service.overallRevenue(branchId)
                )
        );
    }

    @GetMapping("/api/manager/analytics/branches/revenue")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerRevenue() {

        HotelDashboardResponse dashboard = hotel.dashboard(null);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Manager branch revenue fetched",
                        new ManagerDashboardResponse(
                                dashboard.totalRevenue(),
                                dashboard.bookings(),
                                dashboard.occupancyRate().doubleValue(),
                                0L,
                                0L,
                                BigDecimal.ZERO,
                                List.of()
                        )
                )
        );
    }

    @GetMapping("/api/manager/analytics/branches/occupancy")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerOccupancy() {

        HotelDashboardResponse dashboard = hotel.dashboard(null);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Manager branch occupancy fetched",
                        new ManagerDashboardResponse(
                                BigDecimal.ZERO,
                                0L,
                                dashboard.occupancyRate().doubleValue(),
                                0L,
                                0L,
                                BigDecimal.ZERO,
                                List.of()
                        )
                )
        );
    }

    @GetMapping("/api/manager/analytics/branches/attendance")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerAttendance() {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Manager branch attendance fetched",
                        new ManagerDashboardResponse(
                                BigDecimal.ZERO,
                                0L,
                                0.0,
                                0L,
                                0L,
                                BigDecimal.ZERO,
                                List.of()
                        )
                )
        );
    }

    @GetMapping("/api/manager/analytics/branches/dashboard")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerDashboard() {

        HotelDashboardResponse dashboard = hotel.dashboard(null);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Manager branches dashboard fetched",
                        new ManagerDashboardResponse(
                                dashboard.totalRevenue(),
                                dashboard.bookings(),
                                dashboard.occupancyRate().doubleValue(),
                                0L,
                                0L,
                                BigDecimal.ZERO,
                                List.of()
                        )
                )
        );
    }

    @GetMapping("/api/root-admin/analytics/website")
    public ResponseEntity<ApiResponse<RootDashboardResponse>> website() {

        HotelDashboardResponse dashboard = hotel.dashboard(null);

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Website analytics fetched",
                        new RootDashboardResponse(
                                dashboard.totalRevenue(),
                                0L,
                                dashboard.bookings(),
                                dashboard.customers(),
                                List.of()
                        )
                )
        );
    }

    @GetMapping("/api/root-admin/analytics/revenue")
    public ResponseEntity<ApiResponse<RootDashboardResponse>> rootRevenue() {
        return website();
    }
}