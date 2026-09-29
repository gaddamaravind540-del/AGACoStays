package com.agacostays.booking.entity;

import com.agacostays.booking.enums.BookingAction;
import com.agacostays.booking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "booking_history",
       indexes = {
           @Index(name = "idx_booking_history_booking", columnList = "booking_id")
       })
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 40)
    private BookingAction action;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_status", nullable = false, length = 30)
    private BookingStatus bookingStatus;

    @Column(name = "changed_by")
    private Long changedBy;

    @Column(name = "changed_by_role", length = 50)
    private String changedByRole;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
