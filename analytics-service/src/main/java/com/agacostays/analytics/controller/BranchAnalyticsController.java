package com.agacostays.analytics.controller;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BranchAnalyticsController {
    private final BranchAnalyticsService service;
    private final HotelAnalyticsService hotel;
    public BranchAnalyticsController(BranchAnalyticsService service,HotelAnalyticsService hotel){
        this.service=service;this.hotel=hotel;
    }

    @GetMapping("/api/analytics/hotel-branches/{branchId}")
    public ResponseEntity<ApiResponse<HotelDashboardResponse>> branch(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Branch analytics fetched",service.branchHotel(branchId)));
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/overall/revenue")
    public ResponseEntity<ApiResponse<BranchRevenueResponse>> overall(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Branch overall revenue fetched",service.overallRevenue(branchId)));
    }

    @GetMapping("/api/manager/analytics/branches/revenue")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerRevenue(){
        return ResponseEntity.ok(ApiResponse.ok("Manager branch revenue fetched",
            new ManagerDashboardResponse(hotel.dashboard(null).totalRevenue(),hotel.dashboard(null).bookings(),
                hotel.dashboard(null).occupancyRate(),0,0,java.math.BigDecimal.ZERO,java.util.List.of())));
    }

    @GetMapping("/api/manager/analytics/branches/occupancy")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerOccupancy(){
        return ResponseEntity.ok(ApiResponse.ok("Manager branch occupancy fetched",
            new ManagerDashboardResponse(java.math.BigDecimal.ZERO,0,hotel.dashboard(null).occupancyRate(),0,0,java.math.BigDecimal.ZERO,java.util.List.of())));
    }

    @GetMapping("/api/manager/analytics/branches/attendance")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerAttendance(){
        return ResponseEntity.ok(ApiResponse.ok("Manager branch attendance fetched",
            new ManagerDashboardResponse(java.math.BigDecimal.ZERO,0,0,0,0,java.math.BigDecimal.ZERO,java.util.List.of())));
    }

    @GetMapping("/api/manager/analytics/branches/dashboard")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> managerDashboard(){
        return ResponseEntity.ok(ApiResponse.ok("Manager branches dashboard fetched",
            new ManagerDashboardResponse(hotel.dashboard(null).totalRevenue(),hotel.dashboard(null).bookings(),
                hotel.dashboard(null).occupancyRate(),0,0,java.math.BigDecimal.ZERO,java.util.List.of())));
    }

    @GetMapping("/api/root-admin/analytics/website")
    public ResponseEntity<ApiResponse<RootDashboardResponse>> website(){
        return ResponseEntity.ok(ApiResponse.ok("Website analytics fetched",
            new RootDashboardResponse(hotel.dashboard(null).totalRevenue(),0,hotel.dashboard(null).bookings(),
                hotel.dashboard(null).customers(),java.util.List.of())));
    }

    @GetMapping("/api/root-admin/analytics/revenue")
    public ResponseEntity<ApiResponse<RootDashboardResponse>> rootRevenue(){
        return website();
    }
}
