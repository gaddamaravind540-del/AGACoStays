package com.agacostays.booking.dto.request;

import com.agacostays.booking.enums.BookingStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingStatusRequest {
    @NotNull
    private BookingStatus status;
    private String remarks;
}
