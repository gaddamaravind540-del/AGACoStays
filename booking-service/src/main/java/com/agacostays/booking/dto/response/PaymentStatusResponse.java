package com.agacostays.booking.dto.response;

import com.agacostays.booking.enums.PaymentStatus;
import lombok.*;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class PaymentStatusResponse {
    private Long bookingId;
    private PaymentStatus status;
}
