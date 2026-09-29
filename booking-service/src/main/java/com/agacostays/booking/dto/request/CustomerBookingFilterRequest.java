package com.agacostays.booking.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerBookingFilterRequest {
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
}
