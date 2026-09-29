package com.agacostays.booking.security;

import com.agacostays.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class OwnerAccessValidator {

    private final CurrentUserProvider currentUserProvider;

    public OwnerAccessValidator(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    public void validateOwner(Booking booking) {
        if (currentUserProvider.isCustomer()
                && !booking.getCustomerId().equals(currentUserProvider.getCurrentUserId())) {
            throw new com.agacostays.booking.exception.CustomerMismatchException();
        }
    }
}
