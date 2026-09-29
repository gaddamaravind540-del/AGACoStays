package com.agacostays.support.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "feedback",
       indexes = {
           @Index(name = "idx_feedback_branch", columnList = "branch_id"),
           @Index(name = "idx_feedback_booking", columnList = "booking_id"),
           @Index(name = "idx_feedback_customer", columnList = "customer_id")
       })
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "hotel_rating")
    private Integer hotelRating;

    @Column(name = "hotel_comments", columnDefinition = "TEXT")
    private String hotelComments;

    @Column(name = "restaurant_rating")
    private Integer restaurantRating;

    @Column(name = "restaurant_comments", columnDefinition = "TEXT")
    private String restaurantComments;

    @Column(name = "feedback_type", length = 30)
    private String feedbackType;

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
