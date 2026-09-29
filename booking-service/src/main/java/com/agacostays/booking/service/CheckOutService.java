package com.agacostays.booking.service;

import com.agacostays.booking.dto.request.CheckOutRequest;
import com.agacostays.booking.dto.response.CheckOutResponse;

public interface CheckOutService {
    CheckOutResponse checkOut(Long bookingId, CheckOutRequest request);
}
