package com.agacostays.restaurant.client;

import com.agacostays.restaurant.dto.response.PaymentStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="payment-service", url="${services.payment.url}")
public interface PaymentServiceClient {
    @GetMapping("/api/payments/restaurant-order/{orderId}/status")
    PaymentStatusResponse getStatus(@PathVariable Long orderId);
}
