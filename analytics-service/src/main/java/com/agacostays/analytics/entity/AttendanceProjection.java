package com.agacostays.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="attendance_projection", uniqueConstraints={
    @UniqueConstraint(name="uk_attendance_projection_branch_date", columnNames={"branch_id","metric_date"})
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AttendanceProjection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projection_id") private Long projectionId;
    @Column(name="branch_id",nullable=false) private Long branchId;
    @Column(name="metric_date",nullable=false) private LocalDate metricDate;
    @Column(name="present_count",nullable=false) private Long presentCount;
    @Column(name="absent_count",nullable=false) private Long absentCount;
    @Column(name="half_day_count",nullable=false) private Long halfDayCount;
    @Column(name="leave_count",nullable=false) private Long leaveCount;
    @Column(name="total_staff",nullable=false) private Long totalStaff;
    @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
    @PrePersist @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
}
