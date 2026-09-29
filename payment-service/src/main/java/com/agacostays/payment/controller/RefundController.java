package com.agacostays.payment.controller;

import com.agacostays.payment.dto.request.RefundRequest;
import com.agacostays.payment.dto.response.*;
import com.agacostays.payment.service.RefundService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/payments")
public class RefundController {
    private final RefundService service;
    public RefundController(RefundService service){this.service=service;}
    @PostMapping({"/{paymentId}/refund","/{paymentId}/refunds"})
    public ResponseEntity<ApiResponse<RefundResponse>> refund(@PathVariable Long paymentId,@Valid @RequestBody RefundRequest request){return ResponseEntity.ok(ApiResponse.ok("Refund processed",service.refund(paymentId,request)));}
    @GetMapping("/{paymentId}/refunds")
    public ResponseEntity<ApiResponse<List<RefundResponse>>> refunds(@PathVariable Long paymentId){return ResponseEntity.ok(ApiResponse.ok("Refunds fetched",service.refunds(paymentId)));}
}
