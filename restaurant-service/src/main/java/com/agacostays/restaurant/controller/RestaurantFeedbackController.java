package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.request.RestaurantFeedbackRequest;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.RestaurantFeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestaurantFeedbackController {
    private final RestaurantFeedbackService service; public RestaurantFeedbackController(RestaurantFeedbackService service){this.service=service;}
    @PostMapping("/restaurant/feedback") @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<RestaurantFeedbackResponse>> create(@RequestParam Long branchId,@Valid @RequestBody RestaurantFeedbackRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Restaurant feedback submitted",service.create(branchId,req)));
    }
    @GetMapping("/restaurant-admin/hotel-branches/{branchId}/feedback") @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<java.util.List<RestaurantFeedbackResponse>>> list(@PathVariable Long branchId){
        return ResponseEntity.ok(ApiResponse.success("Restaurant feedback fetched",service.byBranch(branchId)));
    }
}
