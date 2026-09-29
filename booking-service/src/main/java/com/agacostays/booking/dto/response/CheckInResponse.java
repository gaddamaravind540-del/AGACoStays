package com.agacostays.booking.dto.response;

import lombok.*;

import java.time.OffsetDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class CheckInResponse {
    private Long bookingId;
    private String status;
    private OffsetDateTime completedAt;
    private String guestName;
}
