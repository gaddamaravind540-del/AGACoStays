package com.agacostays.booking.validation;

import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.exception.CustomerMismatchException;
import org.springframework.stereotype.Service;

@Service
public class BookingOwnershipValidationService {
    public void validate(Long currentCustomerId, Booking booking) {
        if (!booking.getCustomerId().equals(currentCustomerId)) {
            throw new CustomerMismatchException();
        }
    }
}
