package com.agacostays.booking.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class RoomResponse {
    private Long roomId;
    private Long branchId;
    private String roomNumber;
    private String roomType;
    private BigDecimal currentPricePerDay;
    private String status;
}
