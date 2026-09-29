package com.agacostays.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_order_assignment")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantOrderAssignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="assignment_id")
    private Long assignmentId;

    @Column(name="order_id", nullable=false) private Long orderId;
    @Column(name="chef_id") private Long chefId;
    @Column(name="serving_staff_id") private Long servingStaffId;

    @Column(name="assigned_by") private Long assignedBy;
    @Column(name="created_at", nullable=false) private OffsetDateTime createdAt;

    @PrePersist void onCreate() { createdAt = OffsetDateTime.now(); }
}
