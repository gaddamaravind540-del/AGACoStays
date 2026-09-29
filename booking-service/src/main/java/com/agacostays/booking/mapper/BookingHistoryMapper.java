package com.agacostays.booking.mapper;

import com.agacostays.booking.dto.response.BookingHistoryResponse;
import com.agacostays.booking.entity.BookingHistory;
import org.springframework.stereotype.Component;

@Component
public class BookingHistoryMapper {
    public BookingHistoryResponse toResponse(BookingHistory h) {
        return BookingHistoryResponse.builder()
                .historyId(h.getHistoryId())
                .bookingId(h.getBookingId())
                .action(h.getAction())
                .bookingStatus(h.getBookingStatus())
                .changedBy(h.getChangedBy())
                .changedByRole(h.getChangedByRole())
                .remarks(h.getRemarks())
                .createdAt(h.getCreatedAt())
                .build();
    }
}
