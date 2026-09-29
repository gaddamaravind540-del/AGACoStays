package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.ServingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/serving/orders")
public class ServingController {
    private final ServingService service; public ServingController(ServingService service){this.service=service;}
    @PutMapping("/{orderId}/picked-up") @PreAuthorize("hasRole('SERVING_STAFF')")
    public ResponseEntity<ApiResponse<ServingOrderResponse>> picked(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success("Order picked up",service.pickedUp(orderId)));
    }
    @PutMapping("/{orderId}/delivered") @PreAuthorize("hasRole('SERVING_STAFF')")
    public ResponseEntity<ApiResponse<ServingOrderResponse>> delivered(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success("Order delivered",service.delivered(orderId)));
    }
}
