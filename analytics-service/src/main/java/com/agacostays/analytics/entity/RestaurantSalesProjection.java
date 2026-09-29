package com.agacostays.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="restaurant_sales_projection", uniqueConstraints={
    @UniqueConstraint(name="uk_restaurant_sales_branch_date", columnNames={"branch_id","metric_date"})
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantSalesProjection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projection_id") private Long projectionId;
    @Column(name="branch_id",nullable=false) private Long branchId;
    @Column(name="metric_date",nullable=false) private LocalDate metricDate;
    @Column(name="sales_amount",nullable=false,precision=16,scale=2) private BigDecimal salesAmount;
    @Column(name="order_count",nullable=false) private Long orderCount;
    @Column(name="delivered_count",nullable=false) private Long deliveredCount;
    @Column(name="chef_performance_count",nullable=false) private Long chefPerformanceCount;
    @Column(name="serving_performance_count",nullable=false) private Long servingPerformanceCount;
    @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
    @PrePersist @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
}
