package com.agacostays.analytics.controller;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantAnalyticsController {
    private final RestaurantAnalyticsService service;
    public RestaurantAnalyticsController(RestaurantAnalyticsService service){this.service=service;}

    @GetMapping("/api/analytics/restaurant-dashboard")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> dashboard(){return ResponseEntity.ok(ApiResponse.ok("Restaurant dashboard fetched",service.dashboard(null)));}

    @GetMapping("/api/hotel-branches/{branchId}/analytics/restaurant/dashboard")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> branchDashboard(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Branch restaurant dashboard fetched",service.dashboard(branchId)));
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/restaurant/sales")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> sales(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Restaurant sales fetched",service.dashboard(branchId)));
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/restaurant/dish-sales")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> dishSales(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Dish sales fetched",service.dashboard(branchId)));
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/restaurant/chef-performance")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> chef(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Chef performance fetched",service.dashboard(branchId)));
    }

    @GetMapping("/api/hotel-branches/{branchId}/analytics/restaurant/serving-staff-performance")
    public ResponseEntity<ApiResponse<RestaurantDashboardResponse>> serving(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.ok("Serving staff performance fetched",service.dashboard(branchId)));
    }
}
