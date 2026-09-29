package com.agacostays.booking.service.impl;

import com.agacostays.booking.service.BookingPriceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BookingPriceServiceImpl implements BookingPriceService {
    @Override
    public BigDecimal calculate(BigDecimal pricePerDay, int numberOfDays) {
        return pricePerDay.multiply(BigDecimal.valueOf(numberOfDays));
    }
}
