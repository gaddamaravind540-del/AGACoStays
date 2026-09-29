package com.agacostays.booking.mapper;

import com.agacostays.booking.dto.response.BookingResponse;
import com.agacostays.booking.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingResponse toResponse(Booking b) {
        return BookingResponse.builder()
                .bookingId(b.getBookingId())
                .branchId(b.getBranchId())
                .customerId(b.getCustomerId())
                .roomId(b.getRoomId())
                .checkInDate(b.getCheckInDate())
                .checkOutDate(b.getCheckOutDate())
                .checkInTime(b.getCheckInTime())
                .checkOutTime(b.getCheckOutTime())
                .numberOfGuests(b.getNumberOfGuests())
                .numberOfDays(b.getNumberOfDays())
                .pricePerDay(b.getPricePerDayAtBooking())
                .totalAmount(b.getRoomCharges())
                .bookingStatus(b.getBookingStatus())
                .bookingSource(b.getBookingSource())
                .paymentStatus(b.getPaymentStatus())
                .guestName(b.getGuestName())
                .guestIdProofType(b.getGuestIdProofType())
                .checkInCompletedAt(b.getCheckInCompletedAt())
                .checkOutCompletedAt(b.getCheckOutCompletedAt())
                .build();
    }
}
