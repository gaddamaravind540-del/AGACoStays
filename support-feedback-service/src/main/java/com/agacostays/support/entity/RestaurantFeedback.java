package com.agacostays.support.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_feedback",
       indexes = {
           @Index(name = "idx_rest_feedback_branch", columnList = "branch_id"),
           @Index(name = "idx_rest_feedback_order", columnList = "order_id"),
           @Index(name = "idx_rest_feedback_customer", columnList = "customer_id")
       })
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "food_rating", nullable = false)
    private Integer foodRating;

    @Column(name = "taste_rating", nullable = false)
    private Integer tasteRating;

    @Column(name = "delivery_rating", nullable = false)
    private Integer deliveryRating;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
