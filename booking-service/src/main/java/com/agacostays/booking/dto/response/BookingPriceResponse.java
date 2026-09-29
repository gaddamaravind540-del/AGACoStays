package com.agacostays.booking.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingPriceResponse {
    private Long bookingId;
    private BigDecimal pricePerDay;
    private Integer numberOfDays;
    private BigDecimal totalAmount;
}
