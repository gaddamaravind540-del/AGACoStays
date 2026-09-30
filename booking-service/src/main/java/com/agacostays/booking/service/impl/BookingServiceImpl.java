package com.agacostays.booking.service.impl;

import com.agacostays.booking.client.RoomServiceClient;

import com.agacostays.booking.client.UserServiceClient;
import com.agacostays.booking.dto.request.*;
import com.agacostays.booking.dto.response.*;
import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.entity.BookingHistory;
import com.agacostays.booking.enums.*;
import com.agacostays.booking.exception.*;
import com.agacostays.booking.idempotency.IdempotencyService;
import com.agacostays.booking.mapper.BookingHistoryMapper;
import com.agacostays.booking.mapper.BookingMapper;
import com.agacostays.booking.producer.BookingEventProducer;
import com.agacostays.booking.repository.BookingHistoryRepository;
import com.agacostays.booking.repository.BookingRepository;
import com.agacostays.booking.security.CurrentUserProvider;
import com.agacostays.booking.security.OwnerAccessValidator;
import com.agacostays.booking.service.*;
import com.agacostays.booking.util.*;
import com.agacostays.booking.validation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingHistoryRepository historyRepository;
    private final BookingMapper bookingMapper;
    private final BookingHistoryMapper historyMapper;
    private final RoomServiceClient roomServiceClient;
    private final UserServiceClient userServiceClient;
    private final BookingValidationService bookingValidationService;
    private final BookingStatusValidationService statusValidationService;
    private final BookingEventProducer eventProducer;
    private final CurrentUserProvider currentUserProvider;
    private final OwnerAccessValidator ownerAccessValidator;
    private final BookingNotificationService notificationService;
    private final BookingPriceService priceService;
    private final IdempotencyService idempotencyService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              BookingHistoryRepository historyRepository,
                              BookingMapper bookingMapper,
                              BookingHistoryMapper historyMapper,
                              RoomServiceClient roomServiceClient,
                              UserServiceClient userServiceClient,
                              BookingValidationService bookingValidationService,
                              BookingStatusValidationService statusValidationService,
                              BookingEventProducer eventProducer,
                              CurrentUserProvider currentUserProvider,
                              OwnerAccessValidator ownerAccessValidator,
                              BookingNotificationService notificationService,
                              BookingPriceService priceService,
                              IdempotencyService idempotencyService) {
        this.bookingRepository = bookingRepository;
        this.historyRepository = historyRepository;
        this.bookingMapper = bookingMapper;
        this.historyMapper = historyMapper;
        this.roomServiceClient = roomServiceClient;
        this.userServiceClient = userServiceClient;
        this.bookingValidationService = bookingValidationService;
        this.statusValidationService = statusValidationService;
        this.eventProducer = eventProducer;
        this.currentUserProvider = currentUserProvider;
        this.ownerAccessValidator = ownerAccessValidator;
        this.notificationService = notificationService;
        this.priceService = priceService;
        this.idempotencyService = idempotencyService;
    }

    @Override
    @Transactional
    public BookingResponse create(Long branchId, CreateBookingRequest request, String idempotencyKey) {
        Long customerId = currentUserProvider.isCustomer()
                ? currentUserProvider.getCurrentUserId()
                : request.getCustomerId();

        if (customerId == null) {
            throw new CustomerNotFoundException("Customer id is required");
        }
        return createInternal(branchId, customerId, request, idempotencyKey,
                currentUserProvider.getCurrentUserId(), BookingSource.WEBSITE);
    }

    @Override
    @Transactional
    public BookingResponse createForCustomer(Long branchId, Long customerId, CreateBookingRequest request, String idempotencyKey) {
        return createInternal(branchId, customerId, request, idempotencyKey,
                currentUserProvider.getCurrentUserId(), BookingSource.RECEPTIONIST);
    }

    private BookingResponse createInternal(Long branchId, Long customerId, CreateBookingRequest request,
                                           String idempotencyKey, Long actorId, BookingSource source) {
        bookingValidationService.validateCreate(branchId, request);

        try {
            userServiceClient.getCustomer(customerId);
        } catch (Exception ex) {
            throw new CustomerNotFoundException();
        }

        if (idempotencyKey != null && !idempotencyKey.isBlank()) {
            idempotencyService.validateAndStore(idempotencyKey, request.toString() + ":" + branchId + ":" + customerId);
        }

        var room = roomServiceClient.getRoom(branchId, request.getRoomId());
        int days = DateRangeUtil.numberOfDays(request.getCheckInDate(), request.getCheckOutDate());
        BigDecimal price = room.getCurrentPricePerDay();
        BigDecimal total = priceService.calculate(price, days);

        Booking booking = Booking.builder()
                .branchId(branchId)
                .customerId(customerId)
                .roomId(request.getRoomId())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .checkInTime(request.getCheckInTime())
                .checkOutTime(request.getCheckOutTime())
                .numberOfGuests(request.getNumberOfGuests())
                .numberOfDays(days)
                .pricePerDayAtBooking(price)
                .roomCharges(total)
                .bookingStatus(BookingStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .bookingSource(source)
                .createdBy(actorId)
                .updatedBy(actorId)
                .build();

        Booking saved = bookingRepository.save(booking);

        addHistory(saved, BookingAction.CREATED, "Booking created");
        eventProducer.bookingCreated(saved);
        eventProducer.statusChanged(saved);
        notificationService.notifyCreated(saved);

        return bookingMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        ownerAccessValidator.validateOwner(booking);
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings() {
        Long customerId = currentUserProvider.getCurrentUserId();
        return bookingRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream().map(bookingMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<BookingResponse> getBranchBookings(Long branchId, Pageable pageable) {
        Page<Booking> page = bookingRepository.findByBranchId(branchId, pageable);
        return PageResponse.<BookingResponse>builder()
                .content(page.getContent().stream().map(bookingMapper::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public BookingResponse update(Long bookingId, UpdateBookingRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        ownerAccessValidator.validateOwner(booking);
        statusValidationService.requireStatus(booking, BookingStatus.PENDING, BookingStatus.APPROVED);

        if (request.getCheckInDate() != null) booking.setCheckInDate(request.getCheckInDate());
        if (request.getCheckOutDate() != null) booking.setCheckOutDate(request.getCheckOutDate());
        if (request.getCheckInTime() != null) booking.setCheckInTime(request.getCheckInTime());
        if (request.getCheckOutTime() != null) booking.setCheckOutTime(request.getCheckOutTime());
        if (request.getNumberOfGuests() != null) booking.setNumberOfGuests(request.getNumberOfGuests());

        int days = DateRangeUtil.numberOfDays(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setNumberOfDays(days);
        booking.setRoomCharges(priceService.calculate(booking.getPricePerDayAtBooking(), days));
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());

        bookingRepository.save(booking);
        addHistory(booking, BookingAction.UPDATED, "Booking updated");
        eventProducer.statusChanged(booking);

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingStatusResponse approve(Long bookingId, String remarks) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        statusValidationService.requireStatus(booking, BookingStatus.PENDING);

        booking.setBookingStatus(BookingStatus.APPROVED);
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());
        bookingRepository.save(booking);

        addHistory(booking, BookingAction.APPROVED, remarks);
        eventProducer.bookingApproved(booking);
        eventProducer.statusChanged(booking);
        notificationService.notifyStatusChanged(booking);

        return BookingStatusResponse.builder()
                .bookingId(bookingId)
                .bookingStatus(booking.getBookingStatus())
                .message("Booking approved successfully")
                .build();
    }

    @Override
    @Transactional
    public BookingStatusResponse reject(Long bookingId, String remarks) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        statusValidationService.requireStatus(booking, BookingStatus.PENDING);

        booking.setBookingStatus(BookingStatus.REJECTED);
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());
        bookingRepository.save(booking);

        addHistory(booking, BookingAction.REJECTED, remarks);
        eventProducer.bookingRejected(booking, remarks);
        eventProducer.statusChanged(booking);
        notificationService.notifyStatusChanged(booking);

        return BookingStatusResponse.builder()
                .bookingId(bookingId)
                .bookingStatus(booking.getBookingStatus())
                .message("Booking rejected successfully")
                .build();
    }

    @Override
    @Transactional
    public BookingStatusResponse cancel(Long bookingId, BookingCancelRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        ownerAccessValidator.validateOwner(booking);
        statusValidationService.requireStatus(booking, BookingStatus.PENDING, BookingStatus.APPROVED);

        String reason = request.getReason() == null ? "OTHER" : request.getReason().name();
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setCancellationReason(reason);
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());
        bookingRepository.save(booking);

        addHistory(booking, BookingAction.CANCELLED, request.getRemarks());
        eventProducer.bookingCancelled(booking, reason);
        eventProducer.statusChanged(booking);
        notificationService.notifyStatusChanged(booking);

        return BookingStatusResponse.builder()
                .bookingId(bookingId)
                .bookingStatus(booking.getBookingStatus())
                .message("Booking cancelled successfully")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingHistoryResponse> history(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
        ownerAccessValidator.validateOwner(booking);

        return historyRepository.findByBookingIdOrderByCreatedAtDesc(bookingId)
                .stream().map(historyMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> searchByDateRange(Long branchId, BookingDateRangeRequest request) {
        if (!request.getEndDate().isAfter(request.getStartDate())) {
            throw new InvalidDateRangeException();
        }
        return bookingRepository.findOverlappingBookings(
                branchId,
                request.getStartDate(),
                request.getEndDate(),
                List.of(BookingStatus.PENDING, BookingStatus.APPROVED, BookingStatus.CHECKED_IN))
                .stream().map(bookingMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public void updatePaymentStatus(Long bookingId, PaymentStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        booking.setPaymentStatus(status);
        bookingRepository.save(booking);

        addHistory(booking, BookingAction.PAYMENT_UPDATED, "Payment status updated to " + status);
        eventProducer.paymentStatusUpdated(booking);
        eventProducer.statusChanged(booking);
    }

    private void addHistory(Booking booking, BookingAction action, String remarks) {
        historyRepository.save(BookingHistory.builder()
                .bookingId(booking.getBookingId())
                .action(action)
                .bookingStatus(booking.getBookingStatus())
                .changedBy(currentUserProvider.isAuthenticated() ? currentUserProvider.getCurrentUserId() : null)
                .changedByRole(currentUserProvider.isAuthenticated() ? currentUserProvider.getCurrentRole() : null)
                .remarks(remarks)
                .build());
    }
    
    @Override
    @Transactional
    public CheckInResponse checkIn(Long bookingId, CheckInRequest request) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        statusValidationService.requireStatus(
                booking,
                BookingStatus.APPROVED
        );

        booking.setBookingStatus(BookingStatus.CHECKED_IN);
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());

        bookingRepository.save(booking);

        addHistory(
                booking,
                BookingAction.CHECKED_IN,
                "Guest checked in"
        );

        eventProducer.statusChanged(booking);

        return CheckInResponse.builder()
                .bookingId(booking.getBookingId())
                .status(booking.getBookingStatus().name())
                .completedAt(OffsetDateTime.now())
                .guestName(request.getGuestName())
                .build();
    }
    
    @Override
    @Transactional
    public CheckOutResponse checkOut(Long bookingId, CheckOutRequest request) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);

        statusValidationService.requireStatus(
                booking,
                BookingStatus.CHECKED_IN
        );

        booking.setBookingStatus(BookingStatus.CHECKED_OUT);
        booking.setUpdatedBy(currentUserProvider.getCurrentUserId());

        bookingRepository.save(booking);

        addHistory(
                booking,
                BookingAction.CHECKED_OUT,
                request.getRemarks()
        );

        eventProducer.statusChanged(booking);

        return CheckOutResponse.builder()
                .bookingId(booking.getBookingId())
                .status(booking.getBookingStatus().name())
                .completedAt(OffsetDateTime.now())
                .billingStatus(booking.getPaymentStatus().name())
                .build();
    }
}
