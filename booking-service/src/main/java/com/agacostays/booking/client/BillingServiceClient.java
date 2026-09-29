package com.agacostays.booking.client;

import com.agacostays.booking.dto.response.BillingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "billing-service", url = "${services.billing.url}")
public interface BillingServiceClient {

    @PostMapping("/api/billing/bookings/{bookingId}")
    BillingResponse createBookingCharge(@PathVariable Long bookingId);
}
