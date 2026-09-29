package com.agacostays.booking.dto.response;

import com.agacostays.booking.enums.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Long branchId;
    private Long customerId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Integer numberOfGuests;
    private Integer numberOfDays;
    private BigDecimal pricePerDay;
    private BigDecimal totalAmount;
    private BookingStatus bookingStatus;
    private BookingSource bookingSource;
    private PaymentStatus paymentStatus;
    private String guestName;
    private String guestIdProofType;
    private OffsetDateTime checkInCompletedAt;
    private OffsetDateTime checkOutCompletedAt;
}
