package com.agacostays.booking.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BillingResponse {
    private Long bookingId;
    private String status;
    private BigDecimal totalAmount;
}
