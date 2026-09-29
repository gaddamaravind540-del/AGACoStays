package com.agacostays.support.controller;

import com.agacostays.support.dto.request.RestaurantFeedbackRequest;
import com.agacostays.support.dto.response.*;
import com.agacostays.support.service.RestaurantFeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantFeedbackController {

    private final RestaurantFeedbackService service;

    public RestaurantFeedbackController(RestaurantFeedbackService service){this.service=service;}

    @PostMapping("/feedback")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RestaurantFeedbackResponse>> create(
            @Valid @RequestBody RestaurantFeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Restaurant feedback submitted",service.create(request)));
    }

    @GetMapping("/feedback/my-feedback")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<java.util.List<RestaurantFeedbackResponse>>> mine() {
        return ResponseEntity.ok(ApiResponse.success("Restaurant feedback fetched",service.byMyOrders()));
    }

    @GetMapping("/feedback")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','RECEPTIONIST','MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<RestaurantFeedbackResponse>>> byBranch(
            @RequestParam Long branchId) {
        return ResponseEntity.ok(ApiResponse.success("Restaurant feedback fetched",service.byBranch(branchId)));
    }

    @GetMapping("/feedback/order/{orderId}")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','RECEPTIONIST','MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<RestaurantFeedbackResponse>>> byOrder(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Restaurant feedback fetched", service.byOrder(orderId)));
    }
}
