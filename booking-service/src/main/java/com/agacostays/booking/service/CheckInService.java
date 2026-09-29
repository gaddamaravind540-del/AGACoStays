package com.agacostays.booking.service;

import com.agacostays.booking.dto.request.CheckInRequest;
import com.agacostays.booking.dto.response.CheckInResponse;

public interface CheckInService {
    CheckInResponse checkIn(Long bookingId, CheckInRequest request);
}
