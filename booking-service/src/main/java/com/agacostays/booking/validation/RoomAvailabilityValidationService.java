package com.agacostays.booking.validation;

import com.agacostays.booking.client.RoomServiceClient;
import com.agacostays.booking.exception.RoomNotAvailableException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RoomAvailabilityValidationService {

    private final RoomServiceClient roomServiceClient;

    public RoomAvailabilityValidationService(RoomServiceClient roomServiceClient) {
        this.roomServiceClient = roomServiceClient;
    }

    public void validate(Long branchId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        var room = roomServiceClient.getRoom(branchId, roomId);
        if (room == null) {
            throw new com.agacostays.booking.exception.RoomNotFoundException();
        }
        var availability = roomServiceClient.checkAvailability(branchId, roomId, checkIn, checkOut);
        if (availability == null || !availability.available()) {
            throw new RoomNotAvailableException();
        }
    }
}
