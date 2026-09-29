package com.agacostays.support.client;

import com.agacostays.support.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="booking-service", url="${services.booking.url}")
public interface BookingServiceClient {
    @GetMapping("/api/bookings/{bookingId}")
    BookingResponse getBooking(@PathVariable Long bookingId);
}
