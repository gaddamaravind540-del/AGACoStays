package com.agacostays.payment.controller;

import com.agacostays.payment.dto.response.*;
import com.agacostays.payment.service.PaymentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/hotel-branches")
public class ManagerPaymentController {
    private final PaymentService service;
    public ManagerPaymentController(PaymentService service){this.service=service;}
    @GetMapping("/{branchId}/payments")
    public ResponseEntity<ApiResponse<PageResponse<PaymentResponse>>> branchPayments(@PathVariable Long branchId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        return ResponseEntity.ok(ApiResponse.ok("Branch payments fetched", service.branchPayments(branchId, PageRequest.of(page,size))));
    }
}
