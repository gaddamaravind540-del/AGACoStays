package com.agacostays.support.controller;

import com.agacostays.support.dto.request.AssignSupportRequest;
import com.agacostays.support.dto.response.*;
import com.agacostays.support.service.SupportAssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
public class SupportAssignmentController {
    private final SupportAssignmentService service;
    public SupportAssignmentController(SupportAssignmentService service){this.service=service;}

    @PostMapping("/assignments/{requestId}")
    @PreAuthorize("hasAnyRole('MANAGER','RECEPTIONIST')")
    public ResponseEntity<ApiResponse<SupportAssignmentResponse>> assign(
            @PathVariable Long requestId, @Valid @RequestBody AssignSupportRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Support assignment created",service.assign(requestId,request)));
    }
}
