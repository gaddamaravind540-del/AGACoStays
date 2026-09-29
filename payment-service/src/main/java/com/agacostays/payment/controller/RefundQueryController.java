package com.agacostays.payment.controller;

import com.agacostays.payment.dto.response.*;
import com.agacostays.payment.service.RefundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
public class RefundQueryController {
    private final RefundService service;
    public RefundQueryController(RefundService service){this.service=service;}
    @GetMapping("/{refundId}")
    public ResponseEntity<ApiResponse<RefundResponse>> getRefund(@PathVariable Long refundId){return ResponseEntity.ok(ApiResponse.ok("Refund fetched",service.getRefund(refundId)));}
}
