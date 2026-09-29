package com.agacostays.booking.service.impl;

import com.agacostays.booking.client.RoomServiceClient;
import com.agacostays.booking.dto.request.CreateBookingRequest;
import com.agacostays.booking.exception.InvalidDateRangeException;
import com.agacostays.booking.exception.RoomNotAvailableException;
import com.agacostays.booking.service.BookingValidationService;
import org.springframework.stereotype.Service;

@Service
public class BookingValidationServiceImpl implements BookingValidationService {

    private final RoomServiceClient roomServiceClient;

    public BookingValidationServiceImpl(RoomServiceClient roomServiceClient) {
        this.roomServiceClient = roomServiceClient;
    }

    @Override
    public void validateCreate(Long branchId, CreateBookingRequest request) {
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new InvalidDateRangeException();
        }
        var room = roomServiceClient.getRoom(branchId, request.getRoomId());
        if (room == null) {
            throw new com.agacostays.booking.exception.RoomNotFoundException();
        }
        if (!"AVAILABLE".equalsIgnoreCase(room.getStatus())) {
            // Availability for the requested date range is checked separately below.
            if ("MAINTENANCE".equalsIgnoreCase(room.getStatus()) || "CLEANING".equalsIgnoreCase(room.getStatus())) {
                throw new RoomNotAvailableException();
            }
        }
        var availability = roomServiceClient.checkAvailability(
                branchId, request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());
        if (availability == null || !availability.available()) {
            throw new RoomNotAvailableException();
        }
    }
}
