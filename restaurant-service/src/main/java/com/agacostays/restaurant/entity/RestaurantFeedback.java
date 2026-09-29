package com.agacostays.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_feedback")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantFeedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long feedbackId;

    @Column(name="branch_id", nullable=false) private Long branchId;
    @Column(name="restaurant_id", nullable=false) private Long restaurantId;
    @Column(name="order_id", nullable=false) private Long orderId;
    @Column(name="customer_id", nullable=false) private Long customerId;
    @Column(nullable=false) private Integer rating;
    private String comments;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @PrePersist void onCreate() { createdAt = OffsetDateTime.now(); }
}
