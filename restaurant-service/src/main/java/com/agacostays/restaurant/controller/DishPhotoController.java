package com.agacostays.restaurant.controller;
import com.agacostays.restaurant.dto.request.DishPhotoRequest;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.DishPhotoService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DishPhotoController {
    private final DishPhotoService service; public DishPhotoController(DishPhotoService service){this.service=service;}

    @PostMapping("/restaurant-admin/menu/{menuItemId}/photos")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<DishPhotoResponse>> add(@PathVariable Long menuItemId,@RequestParam Long branchId,@Valid @RequestBody DishPhotoRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Dish photo added",service.add(branchId,menuItemId,req)));
    }

    @GetMapping("/hotel-branches/{branchId}/restaurant/menu/{menuItemId}/photos")
    public ResponseEntity<ApiResponse<java.util.List<DishPhotoResponse>>> list(@PathVariable Long branchId,@PathVariable Long menuItemId){
        return ResponseEntity.ok(ApiResponse.success("Dish photos fetched",service.list(menuItemId)));
    }

    @DeleteMapping("/restaurant-admin/menu/photos/{photoId}")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long photoId){
        service.delete(photoId); return ResponseEntity.ok(ApiResponse.success("Dish photo deleted",null));
    }
}
