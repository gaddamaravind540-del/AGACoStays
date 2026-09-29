package com.agacostays.booking.dto.request;

import com.agacostays.booking.enums.BookingStatus;
import lombok.Data;

@Data
public class BookingSearchRequest {
    private BookingStatus status;
    private Long customerId;
    private Long roomId;
}
