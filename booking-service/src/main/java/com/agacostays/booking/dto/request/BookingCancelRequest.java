package com.agacostays.booking.dto.request;

import com.agacostays.booking.enums.CancellationReason;
import lombok.Data;

@Data
public class BookingCancelRequest {
    private CancellationReason reason;
    private String remarks;
}
