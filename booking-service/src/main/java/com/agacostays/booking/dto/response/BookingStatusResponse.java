package com.agacostays.booking.dto.response;

import com.agacostays.booking.enums.BookingStatus;
import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingStatusResponse {
    private Long bookingId;
    private BookingStatus bookingStatus;
    private String message;
}
