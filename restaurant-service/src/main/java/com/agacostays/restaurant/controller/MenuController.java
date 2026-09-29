package com.agacostays.restaurant.controller;

import com.agacostays.restaurant.dto.request.*;
import com.agacostays.restaurant.dto.response.*;
import com.agacostays.restaurant.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MenuController {
    private final MenuService service;
    public MenuController(MenuService service){this.service=service;}

    @PostMapping("/restaurant-admin/hotel-branches/{branchId}/menu")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<MenuItemResponse>> create(@PathVariable Long branchId,@Valid @RequestBody CreateMenuItemRequest req){
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Menu item created",service.create(branchId,req)));
    }

    @GetMapping("/hotel-branches/{branchId}/restaurant/menu")
    public ResponseEntity<ApiResponse<java.util.List<MenuItemResponse>>> list(@PathVariable Long branchId, MenuSearchRequest req){
        return ResponseEntity.ok(ApiResponse.success("Menu fetched",service.list(branchId,req)));
    }

    @GetMapping("/hotel-branches/{branchId}/restaurant/menu/{menuItemId}")
    public ResponseEntity<ApiResponse<MenuItemResponse>> get(@PathVariable Long branchId,@PathVariable Long menuItemId){
        return ResponseEntity.ok(ApiResponse.success("Menu item fetched",service.get(branchId,menuItemId)));
    }

    @PutMapping("/restaurant-admin/menu/{menuItemId}")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<MenuItemResponse>> update(@PathVariable Long menuItemId,@Valid @RequestBody UpdateMenuItemRequest req){
        return ResponseEntity.ok(ApiResponse.success("Menu item updated",service.update(menuItemId,req)));
    }

    @DeleteMapping("/restaurant-admin/hotel-branches/{branchId}/restaurant/menu/{menuItemId}")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long branchId,@PathVariable Long menuItemId){
        service.delete(menuItemId); return ResponseEntity.ok(ApiResponse.success("Menu item deleted",null));
    }

    @PutMapping("/restaurant-admin/menu/{menuItemId}/status")
    @PreAuthorize("hasAnyRole('RESTAURANT_ADMIN','MANAGER')")
    public ResponseEntity<ApiResponse<MenuItemResponse>> availability(@PathVariable Long menuItemId,@RequestParam boolean available){
        return ResponseEntity.ok(ApiResponse.success("Availability updated",service.updateAvailability(menuItemId,available)));
    }
}
