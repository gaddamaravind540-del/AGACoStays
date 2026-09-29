package com.agacostays.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="hotel_revenue_projection", uniqueConstraints={
    @UniqueConstraint(name="uk_hotel_revenue_branch_date", columnNames={"branch_id","metric_date"})
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class HotelRevenueProjection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projection_id") private Long projectionId;
    @Column(name="branch_id",nullable=false) private Long branchId;
    @Column(name="metric_date",nullable=false) private LocalDate metricDate;
    @Column(name="room_revenue",nullable=false,precision=16,scale=2) private BigDecimal roomRevenue;
    @Column(name="restaurant_revenue",nullable=false,precision=16,scale=2) private BigDecimal restaurantRevenue;
    @Column(name="total_revenue",nullable=false,precision=16,scale=2) private BigDecimal totalRevenue;
    @Column(name="booking_count",nullable=false) private Long bookingCount;
    @Column(name="occupied_room_count",nullable=false) private Long occupiedRoomCount;
    @Column(name="customer_count",nullable=false) private Long customerCount;
    @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
    @PrePersist @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
}
