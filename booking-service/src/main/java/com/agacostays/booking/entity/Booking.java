package com.agacostays.booking.entity;

import com.agacostays.booking.enums.BookingSource;
import com.agacostays.booking.enums.BookingStatus;
import com.agacostays.booking.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "bookings",
       indexes = {
           @Index(name = "idx_booking_branch", columnList = "branch_id"),
           @Index(name = "idx_booking_customer", columnList = "customer_id"),
           @Index(name = "idx_booking_room", columnList = "room_id"),
           @Index(name = "idx_booking_dates", columnList = "check_in_date,check_out_date")
       })
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime;

    @Column(name = "number_of_guests", nullable = false)
    private Integer numberOfGuests;

    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @Column(name = "price_per_day_at_booking", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerDayAtBooking;

    @Column(name = "room_charges", nullable = false, precision = 12, scale = 2)
    private BigDecimal roomCharges;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false, length = 30)
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_source", nullable = false, length = 30)
    private BookingSource bookingSource;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "guest_id_proof_type", length = 30)
    private String guestIdProofType;

    @Column(name = "guest_id_proof_number")
    private String guestIdProofNumber;

    @Column(name = "check_in_completed_at")
    private OffsetDateTime checkInCompletedAt;

    @Column(name = "check_out_completed_at")
    private OffsetDateTime checkOutCompletedAt;

    @Column(name = "cancellation_reason", length = 60)
    private String cancellationReason;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
