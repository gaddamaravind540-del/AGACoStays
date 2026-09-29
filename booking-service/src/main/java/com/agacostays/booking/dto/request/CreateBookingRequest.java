package com.agacostays.booking.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateBookingRequest {

    @NotNull
    private Long roomId;

    private Long customerId;

    @NotNull
    @FutureOrPresent
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    private LocalTime checkInTime;

    @NotNull
    private LocalTime checkOutTime;

    @NotNull
    @Min(1)
    @Max(20)
    private Integer numberOfGuests;
}
