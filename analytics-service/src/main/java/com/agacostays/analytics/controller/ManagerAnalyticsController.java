package com.agacostays.analytics.controller;
import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.service.ManagerAnalyticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
public class ManagerAnalyticsController {
    private final ManagerAnalyticsService service;
    public ManagerAnalyticsController(ManagerAnalyticsService service){this.service=service;}
    @GetMapping("/api/analytics/manager-dashboard")
    public ResponseEntity<ApiResponse<ManagerDashboardResponse>> dashboard(){
        return ResponseEntity.ok(ApiResponse.ok("Manager dashboard fetched",service.dashboard()));
    }
}
