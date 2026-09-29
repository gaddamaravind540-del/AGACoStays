package com.agacostays.booking.service.impl;

import com.agacostays.booking.client.BillingServiceClient;
import com.agacostays.booking.dto.request.CheckOutRequest;
import com.agacostays.booking.dto.response.CheckOutResponse;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingAction;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.exception.BookingNotFoundException;
import com.agacostays.booking.producer.BookingEventProducer;
import com.agacostays.booking.repository.BookingHistoryRepository;
import com.agacostays.booking.repository.BookingRepository;
import com.agacostays.booking.service.CheckOutService;
import com.agacostays.booking.validation.CheckOutValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class CheckOutServiceImpl implements CheckOutService {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository historyRepository;
    private final CheckOutValidationService validator;
    private final BookingEventProducer eventProducer;
    private final BillingServiceClient billingServiceClient;

    public CheckOutServiceImpl(BookingRepository bookingRepository,
                               BookingHistoryRepository historyRepository,
                               CheckOutValidationService validator,
                               BookingEventProducer eventProducer,
                               BillingServiceClient billingServiceClient) {
        this.bookingRepository = bookingRepository;
        this.historyRepository = historyRepository;
        this.validator = validator;
        this.eventProducer = eventProducer;
        this.billingServiceClient = billingServiceClient;
    }

    @Override
    @Transactional
    public CheckOutResponse checkOut(Long bookingId, CheckOutRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        validator.validate(booking);

        booking.setBookingStatus(BookingStatus.CHECKED_OUT);
        booking.setCheckOutCompletedAt(OffsetDateTime.now());
        bookingRepository.save(booking);

        historyRepository.save(com.agacostays.booking.entity.BookingHistory.builder()
                .bookingId(bookingId)
                .action(BookingAction.CHECKED_OUT)
                .bookingStatus(BookingStatus.CHECKED_OUT)
                .remarks(request.getRemarks())
                .build());

        eventProducer.checkoutCompleted(booking);
        eventProducer.statusChanged(booking);

        String billingStatus = "EVENT_PENDING";
        try {
            if (billingServiceClient != null) {
                var bill = billingServiceClient.createBookingCharge(bookingId);
                if (bill != null && bill.getStatus() != null) {
                    billingStatus = bill.getStatus();
                }
            }
        } catch (Exception ignored) {
            // Billing is a downstream service; checkout remains committed and Billing also consumes checkout event.
        }

        return CheckOutResponse.builder()
                .bookingId(bookingId)
                .status("COMPLETED")
                .completedAt(booking.getCheckOutCompletedAt())
                .billingStatus(billingStatus)
                .build();
    }
}
