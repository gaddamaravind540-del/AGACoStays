package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.KitchenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kitchen/orders")
public class KitchenController {
    private final KitchenService service; public KitchenController(KitchenService service){this.service=service;}
    @PutMapping("/{orderId}/preparing") @PreAuthorize("hasRole('CHEF')")
    public ResponseEntity<ApiResponse<KitchenOrderResponse>> preparing(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success("Order marked preparing",service.preparing(orderId)));
    }
    @PutMapping("/{orderId}/ready") @PreAuthorize("hasRole('CHEF')")
    public ResponseEntity<ApiResponse<KitchenOrderResponse>> ready(@PathVariable Long orderId){
        return ResponseEntity.ok(ApiResponse.success("Order marked ready",service.ready(orderId)));
    }
}
