package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.request.*;
import com.agacostays.restaurant.dto.response.ApiResponse;
import com.agacostays.restaurant.service.RestaurantOrderAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant-admin/orders")
public class RestaurantAssignmentController {
    private final RestaurantOrderAssignmentService service; public RestaurantAssignmentController(RestaurantOrderAssignmentService service){this.service=service;}
    @PutMapping("/{orderId}/assign-chef") @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Void>> chef(@PathVariable Long orderId,@Valid @RequestBody AssignChefRequest req){
        service.assignChef(orderId,req.getChefId()); return ResponseEntity.ok(ApiResponse.success("Chef assigned",null));
    }
    @PutMapping("/{orderId}/assign-serving-staff") @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Void>> staff(@PathVariable Long orderId,@Valid @RequestBody AssignServingStaffRequest req){
        service.assignServingStaff(orderId,req.getServingStaffId()); return ResponseEntity.ok(ApiResponse.success("Serving staff assigned",null));
    }
}
