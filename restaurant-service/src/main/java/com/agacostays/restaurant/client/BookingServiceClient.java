package com.agacostays.restaurant.client;

import com.agacostays.restaurant.dto.response.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="booking-service", url="${services.booking.url}")
public interface BookingServiceClient {
    @GetMapping("/api/bookings/{bookingId}")
    BookingResponse getBooking(@PathVariable Long bookingId);
}
