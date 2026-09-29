package com.agacostays.booking.service;

import java.math.BigDecimal;

public interface BookingPriceService {
    BigDecimal calculate(BigDecimal pricePerDay, int numberOfDays);
}
