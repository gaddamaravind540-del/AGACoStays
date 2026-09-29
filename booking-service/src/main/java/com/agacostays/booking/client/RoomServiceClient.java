package com.agacostays.booking.client;

import com.agacostays.booking.dto.response.RoomResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(name = "room-service", url = "${services.room.url}")
public interface RoomServiceClient {

    @GetMapping("/api/hotel-branches/{branchId}/rooms/{roomId}")
    RoomResponse getRoom(
            @PathVariable Long branchId,
            @PathVariable Long roomId
    );

    @GetMapping("/api/hotel-branches/{branchId}/rooms/availability")
    RoomAvailabilityResponse checkAvailability(
            @PathVariable Long branchId,
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate
    );

    record RoomAvailabilityResponse(Long roomId, boolean available, String status) {}
}
