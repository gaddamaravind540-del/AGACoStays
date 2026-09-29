package com.agacostays.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="feedback_projection", uniqueConstraints={
    @UniqueConstraint(name="uk_feedback_projection_branch_date", columnNames={"branch_id","metric_date"})
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class FeedbackProjection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projection_id") private Long projectionId;
    @Column(name="branch_id",nullable=false) private Long branchId;
    @Column(name="metric_date",nullable=false) private LocalDate metricDate;
    @Column(name="hotel_rating_sum",nullable=false,precision=16,scale=2) private BigDecimal hotelRatingSum;
    @Column(name="restaurant_rating_sum",nullable=false,precision=16,scale=2) private BigDecimal restaurantRatingSum;
    @Column(name="feedback_count",nullable=false) private Long feedbackCount;
    @Column(name="restaurant_feedback_count",nullable=false) private Long restaurantFeedbackCount;
    @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
    @PrePersist @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
}
