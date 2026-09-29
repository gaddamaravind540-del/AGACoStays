package com.agacostays.booking.repository;

import com.agacostays.booking.entity.Booking;
import com.agacostays.booking.enums.BookingStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByBranchIdAndBookingStatus(Long branchId, BookingStatus status);

    List<Booking> findByCustomerIdOrderByCreatedAtDesc(Long customerId);

    boolean existsByRoomIdAndBookingStatusInAndCheckInDateLessThanAndCheckOutDateGreaterThan(
            Long roomId,
            Collection<BookingStatus> statuses,
            LocalDate checkOutDate,
            LocalDate checkInDate
    );

    Page<Booking> findByBranchId(Long branchId, Pageable pageable);

    List<Booking> findByBranchIdAndCheckInDateLessThanEqualAndCheckOutDateGreaterThanEqual(
            Long branchId, LocalDate checkInDate, LocalDate checkOutDate
    );

    List<Booking> findByBookingStatusAndCreatedAtBefore(BookingStatus status, java.time.OffsetDateTime cutoff);

    @Query("""
        select b from Booking b
        where b.branchId = :branchId
          and b.checkInDate < :endDate
          and b.checkOutDate > :startDate
          and b.bookingStatus in :statuses
        order by b.checkInDate
    """)
    List<Booking> findOverlappingBookings(
            @Param("branchId") Long branchId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("statuses") Collection<BookingStatus> statuses
    );
}
