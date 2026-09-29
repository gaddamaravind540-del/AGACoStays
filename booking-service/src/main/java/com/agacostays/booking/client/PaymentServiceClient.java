package com.agacostays.booking.client;

import com.agacostays.booking.dto.response.PaymentStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", url = "${services.payment.url}")
public interface PaymentServiceClient {

    @GetMapping("/api/payments/booking/{bookingId}/status")
    PaymentStatusResponse getBookingPaymentStatus(@PathVariable Long bookingId);
}
