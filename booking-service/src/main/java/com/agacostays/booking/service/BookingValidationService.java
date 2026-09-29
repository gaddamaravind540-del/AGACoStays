package com.agacostays.booking.service;

import com.agacostays.booking.dto.request.CreateBookingRequest;

public interface BookingValidationService {
    void validateCreate(Long branchId, CreateBookingRequest request);
}
