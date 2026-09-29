package com.agacostays.analytics.controller;
import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.service.RootAdminAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class RootAdminAnalyticsController {
    private final RootAdminAnalyticsService service;
    public RootAdminAnalyticsController(RootAdminAnalyticsService service){this.service=service;}
    @GetMapping("/api/analytics/root-admin-dashboard")
    public ResponseEntity<ApiResponse<RootDashboardResponse>> dashboard(){
        return ResponseEntity.ok(ApiResponse.ok("Root admin dashboard fetched",service.dashboard()));
    }
}
