package com.agacostays.restaurant.client;

import com.agacostays.restaurant.dto.response.BillingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="billing-service", url="${services.billing.url}")
public interface BillingServiceClient {
    @PostMapping("/api/billing/restaurant-orders/{orderId}")
    BillingResponse addOrderToBill(@PathVariable Long orderId);
}
