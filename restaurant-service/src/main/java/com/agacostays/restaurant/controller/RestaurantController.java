package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.request.RestaurantRequest;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestaurantController {
    private final RestaurantService service;
    public RestaurantController(RestaurantService service){this.service=service;}

    @GetMapping("/hotel-branches/{branchId}/restaurant")
    public ResponseEntity<ApiResponse<RestaurantResponse>> get(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.success("Restaurant fetched", service.get(branchId)));
    }

    @PutMapping("/restaurant-admin/hotel-branches/{branchId}/restaurant")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<RestaurantResponse>> update(@PathVariable Long branchId,@Valid @RequestBody RestaurantRequest request){
        return ResponseEntity.ok(ApiResponse.success("Restaurant updated",service.update(branchId,request)));
    }

    @PostMapping("/manager/hotel-branches/{branchId}/restaurant")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<RestaurantResponse>> create(@PathVariable Long branchId,@Valid @RequestBody RestaurantRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Restaurant created",service.create(branchId,request)));
    }
}
