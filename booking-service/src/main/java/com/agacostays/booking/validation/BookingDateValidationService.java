package com.agacostays.booking.validation;

import com.agacostays.booking.dto.request.CreateBookingRequest;
import com.agacostays.booking.exception.InvalidDateRangeException;
import org.springframework.stereotype.Service;

@Service
public class BookingDateValidationService {
    public void validate(CreateBookingRequest request) {
        if (request.getCheckInDate() == null || request.getCheckOutDate() == null
                || !request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new InvalidDateRangeException();
        }
    }
}
