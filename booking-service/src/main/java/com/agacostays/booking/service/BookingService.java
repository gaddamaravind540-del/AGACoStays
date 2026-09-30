package com.agacostays.booking.service;

import com.agacostays.booking.dto.request.*;
import com.agacostays.booking.dto.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
	BookingResponse create(Long branchId, CreateBookingRequest request, String idempotencyKey);

	BookingResponse createForCustomer(Long branchId, Long customerId, CreateBookingRequest request,
			String idempotencyKey);

	BookingResponse getById(Long bookingId);

	List<BookingResponse> getMyBookings();

	PageResponse<BookingResponse> getBranchBookings(Long branchId, Pageable pageable);

	BookingResponse update(Long bookingId, UpdateBookingRequest request);

	BookingStatusResponse approve(Long bookingId, String remarks);

	BookingStatusResponse reject(Long bookingId, String remarks);

	BookingStatusResponse cancel(Long bookingId, BookingCancelRequest request);

	CheckInResponse checkIn(Long bookingId, CheckInRequest request);

	CheckOutResponse checkOut(Long bookingId, CheckOutRequest request);

	List<BookingHistoryResponse> history(Long bookingId);

	List<BookingResponse> searchByDateRange(Long branchId, BookingDateRangeRequest request);

	void updatePaymentStatus(Long bookingId, com.agacostays.booking.enums.PaymentStatus status);
}
