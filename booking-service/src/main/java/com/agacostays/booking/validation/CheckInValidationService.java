package com.agacostays.booking.validation;

import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.CheckInNotAllowedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CheckInValidationService {
    public void validate(Booking booking) {
        if (booking.getBookingStatus() != BookingStatus.APPROVED
                && booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new CheckInNotAllowedException();
        }
        if (LocalDate.now().isBefore(booking.getCheckInDate())) {
            throw new CheckInNotAllowedException("Check-in date has not arrived");
        }
    }
}
