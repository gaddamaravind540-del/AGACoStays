package com.agacostays.restaurant.controller;

import com.agacostays.restaurant.dto.request.CreateFoodOrderRequest;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.enums.FoodOrderStatus;
import com.agacostays.restaurant.service.FoodOrderService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FoodOrderController {

    private final FoodOrderService service;

    public FoodOrderController(FoodOrderService service) {
        this.service = service;
    }

    @PostMapping("/hotel-branches/{branchId}/restaurant/orders")
    @PreAuthorize("hasAnyRole('CUSTOMER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<FoodOrderResponse>> createBranchOrder(
            @PathVariable Long branchId,
            @Valid @RequestBody CreateFoodOrderRequest req) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Food order placed", service.create(branchId, req)));
    }

    @PostMapping("/restaurant/orders")
    @PreAuthorize("hasAnyRole('CUSTOMER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<FoodOrderResponse>> createOrder(
            @RequestParam Long branchId,
            @Valid @RequestBody CreateFoodOrderRequest req) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Food order placed", service.create(branchId, req)));
    }

    @GetMapping("/restaurant/orders/{orderId}")
    public ResponseEntity<ApiResponse<FoodOrderResponse>> get(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Food order fetched", service.get(orderId)));
    }

    @GetMapping("/restaurant/orders/my-orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<java.util.List<FoodOrderResponse>>> myOrders() {
        return ResponseEntity.ok(ApiResponse.success("Orders fetched", service.myOrders()));
    }

    @PutMapping("/restaurant-admin/orders/{orderId}/accept")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<FoodOrderResponse>> accept(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order accepted",
                service.updateStatus(orderId, FoodOrderStatus.ACCEPTED)));
    }

    @PutMapping("/restaurant-admin/orders/{orderId}/reject")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<FoodOrderResponse>> reject(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order rejected",
                service.updateStatus(orderId, FoodOrderStatus.REJECTED)));
    }
}
