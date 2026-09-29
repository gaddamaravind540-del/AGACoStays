package com.agacostays.booking.dto.response;

import com.agacostays.booking.enums.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingHistoryResponse {
    private Long historyId;
    private Long bookingId;
    private BookingAction action;
    private BookingStatus bookingStatus;
    private Long changedBy;
    private String changedByRole;
    private String remarks;
    private OffsetDateTime createdAt;
}
