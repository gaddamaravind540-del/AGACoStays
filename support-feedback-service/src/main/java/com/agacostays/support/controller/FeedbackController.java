package com.agacostays.support.controller;

import com.agacostays.support.dto.request.FeedbackRequest;
import com.agacostays.support.dto.response.*;
import com.agacostays.support.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService service;

    public FeedbackController(FeedbackService service){this.service=service;}

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<FeedbackResponse>> create(@Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Feedback submitted",service.create(request)));
    }

    @GetMapping("/my-feedback")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<java.util.List<FeedbackResponse>>> myFeedback() {
        return ResponseEntity.ok(ApiResponse.success("Feedback fetched",service.myFeedback()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
    public ResponseEntity<ApiResponse<java.util.List<FeedbackResponse>>> byBranch(@RequestParam Long branchId) {
        return ResponseEntity.ok(ApiResponse.success("Feedback fetched",service.byBranch(branchId)));
    }

    @GetMapping("/branch/{branchId}")
    @PreAuthorize("hasAnyRole('MANAGER','ROOT_ADMIN')")
    public ResponseEntity<ApiResponse<java.util.List<FeedbackResponse>>> branch(@PathVariable Long branchId) {
        return ResponseEntity.ok(ApiResponse.success("Feedback fetched",service.byBranch(branchId)));
    }

    @PutMapping("/{feedbackId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ApiResponse<FeedbackResponse>> update(
            @PathVariable Long feedbackId, @Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Feedback updated",service.update(feedbackId,request)));
    }

    @DeleteMapping("/{feedbackId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ROOT_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long feedbackId) {
        service.delete(feedbackId);
        return ResponseEntity.ok(ApiResponse.success("Feedback removed",null));
    }
}
