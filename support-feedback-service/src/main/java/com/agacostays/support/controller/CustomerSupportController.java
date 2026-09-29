package com.agacostays.support.controller;

import com.agacostays.support.dto.request.*;
import com.agacostays.support.dto.response.*;
import com.agacostays.support.service.CustomerSupportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
public class CustomerSupportController {

    private final CustomerSupportService service;

    public CustomerSupportController(CustomerSupportService service) {
        this.service=service;
    }

    @PostMapping("/requests")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<SupportRequestResponse>> create(@Valid @RequestBody CreateSupportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Support request created", service.create(request)));
    }

    @GetMapping("/requests/my-requests")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<java.util.List<SupportRequestResponse>>> mine() {
        return ResponseEntity.ok(ApiResponse.success("Support requests fetched", service.myRequests()));
    }

    @GetMapping("/requests/{requestId}")
    public ResponseEntity<ApiResponse<SupportRequestResponse>> get(@PathVariable Long requestId) {
        return ResponseEntity.ok(ApiResponse.success("Support request fetched", service.get(requestId)));
    }

    @PutMapping("/requests/{requestId}/status")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST','CHEF','SERVING_STAFF','HOUSEKEEPING_STAFF')")
    public ResponseEntity<ApiResponse<SupportRequestResponse>> status(
            @PathVariable Long requestId,
            @Valid @RequestBody UpdateSupportStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Support status updated", service.updateStatus(requestId,request)));
    }

    @PostMapping("/requests/{requestId}/assign")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<SupportRequestResponse>> assign(
            @PathVariable Long requestId,
            @Valid @RequestBody AssignSupportRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Support request assigned", service.assign(requestId,request)));
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST','ROOT_ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<SupportRequestResponse>>> search(
            @RequestParam Long branchId, Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success("Support requests fetched", service.search(branchId,pageable)));
    }
}
