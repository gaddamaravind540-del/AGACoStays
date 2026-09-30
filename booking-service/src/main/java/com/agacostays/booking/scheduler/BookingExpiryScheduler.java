package com.agacostays.booking.scheduler;

import com.agacostays.booking.constants.BookingConstants;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.entity.BookingHistory;
import com.agacostays.booking.enums.BookingAction;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.producer.BookingEventProducer;
import com.agacostays.booking.repository.BookingHistoryRepository;
import com.agacostays.booking.repository.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Component
public class BookingExpiryScheduler {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository historyRepository;
    private final BookingEventProducer eventProducer;

    public BookingExpiryScheduler(BookingRepository bookingRepository,
                                   BookingHistoryRepository historyRepository,
                                   BookingEventProducer eventProducer) {
        this.bookingRepository = bookingRepository;
        this.historyRepository = historyRepository;
        this.eventProducer = eventProducer;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void expirePendingBookings() {
        OffsetDateTime cutoff = OffsetDateTime.now().minusMinutes(BookingConstants.EXPIRY_MINUTES);

        for (Booking booking : bookingRepository.findByBookingStatusAndCreatedAtBefore(BookingStatus.PENDING, cutoff)) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            booking.setCancellationReason("PENDING_TIMEOUT");
            bookingRepository.save(booking);

            historyRepository.save(BookingHistory.builder()
                    .bookingId(booking.getBookingId())
                    .action(BookingAction.CANCELLED)
                    .bookingStatus(BookingStatus.CANCELLED)
                    .remarks("Automatically expired after pending timeout")
                    .build());

            eventProducer.bookingCancelled(booking, "PENDING_TIMEOUT");
            eventProducer.statusChanged(booking);
        }
    }
}
