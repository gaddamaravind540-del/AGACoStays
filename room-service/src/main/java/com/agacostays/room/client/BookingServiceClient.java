package com.agacostays.room.client;

import com.agacostays.room.config.FeignClientConfig;
import com.agacostays.room.dto.response.BookingStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * The PDF specifies a BookingServiceClient but not its exact internal endpoint.
 * This endpoint is an implementation assumption used by the availability flow.
 */
@FeignClient(name = "booking-service", url = "${services.booking.url}", configuration = FeignClientConfig.class)
public interface BookingServiceClient {
    @GetMapping("/internal/api/bookings/room-availability")
    BookingStatusResponse checkRoomAvailability(@RequestParam Long roomId,
                                                @RequestParam LocalDate checkInDate,
                                                @RequestParam LocalDate checkOutDate);
}
