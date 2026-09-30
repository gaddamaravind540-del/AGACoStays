package com.agacostays.payment.controller;

import com.agacostays.payment.dto.request.PaymentWebhookRequest;
import com.agacostays.payment.dto.response.ApiResponse;
import com.agacostays.payment.service.PaymentWebhookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentWebhookController {

    private final PaymentWebhookService service;
    private final ObjectMapper mapper;

    public PaymentWebhookController(
            PaymentWebhookService service,
            ObjectMapper mapper) {

        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/webhook")
    public ResponseEntity<ApiResponse<Void>> webhook(
            @RequestBody String raw,
            @RequestHeader(
                    value = "X-Razorpay-Signature",
                    required = false
            ) String signature) throws Exception {

        PaymentWebhookRequest request =
                mapper.readValue(
                        raw,
                        PaymentWebhookRequest.class
                );

        service.process(
                request,
                raw,
                signature
        );

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Webhook processed",
                        null
                )
        );
    }

    @PostMapping("/webhooks/razorpay")
    public ResponseEntity<ApiResponse<Void>> razorpayWebhook(
            @RequestBody String raw,
            @RequestHeader(
                    value = "X-Razorpay-Signature",
                    required = false
            ) String signature) throws Exception {

        return webhook(
                raw,
                signature
        );
    }
}