package com.agacostays.booking.service.impl;

import com.agacostays.booking.dto.request.CheckInRequest;
import com.agacostays.booking.dto.response.CheckInResponse;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingAction;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.BookingNotFoundException;
import com.agacostays.booking.mapper.BookingMapper;
import com.agacostays.booking.producer.BookingEventProducer;
import com.agacostays.booking.repository.BookingHistoryRepository;
import com.agacostays.booking.repository.BookingRepository;
import com.agacostays.booking.service.CheckInService;
import com.agacostays.booking.validation.CheckInValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class CheckInServiceImpl implements CheckInService {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository historyRepository;
    private final CheckInValidationService validator;
    private final BookingEventProducer eventProducer;

    public CheckInServiceImpl(BookingRepository bookingRepository,
                              BookingHistoryRepository historyRepository,
                              CheckInValidationService validator,
                              BookingEventProducer eventProducer) {
        this.bookingRepository = bookingRepository;
        this.historyRepository = historyRepository;
        this.validator = validator;
        this.eventProducer = eventProducer;
    }

    @Override
    @Transactional
    public CheckInResponse checkIn(Long bookingId, CheckInRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        validator.validate(booking);

        booking.setBookingStatus(BookingStatus.CHECKED_IN);
        booking.setGuestName(request.getGuestName());
        booking.setGuestIdProofType(request.getGuestIdProofType().name());
        booking.setGuestIdProofNumber(request.getGuestIdProofNumber());
        booking.setCheckInCompletedAt(OffsetDateTime.now());
        bookingRepository.save(booking);

        historyRepository.save(com.agacostays.booking.entity.BookingHistory.builder()
                .bookingId(bookingId)
                .action(BookingAction.CHECKED_IN)
                .bookingStatus(BookingStatus.CHECKED_IN)
                .remarks("Guest checked in")
                .build());

        eventProducer.checkInCompleted(booking);
        eventProducer.statusChanged(booking);

        return CheckInResponse.builder()
                .bookingId(bookingId)
                .status("COMPLETED")
                .completedAt(booking.getCheckInCompletedAt())
                .guestName(booking.getGuestName())
                .build();
    }
}
