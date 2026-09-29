package com.agacostays.booking.validation;

import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.CheckOutNotAllowedException;
import org.springframework.stereotype.Service;

@Service
public class CheckOutValidationService {
    public void validate(Booking booking) {
        if (booking.getBookingStatus() != BookingStatus.CHECKED_IN) {
            throw new CheckOutNotAllowedException();
        }
    }
}
