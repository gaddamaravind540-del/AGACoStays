package com.agacostays.booking.dto.response;

import com.agacostays.booking.enums.BookingStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingSummaryResponse {
    private Long bookingId;
    private Long roomId;
    private Long customerId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus status;
    private BigDecimal totalAmount;
}
