package com.agacostays.analytics.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="payroll_projection", uniqueConstraints={
    @UniqueConstraint(name="uk_payroll_projection_branch_date", columnNames={"branch_id","metric_date"})
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PayrollProjection {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="projection_id") private Long projectionId;
    @Column(name="branch_id",nullable=false) private Long branchId;
    @Column(name="metric_date",nullable=false) private LocalDate metricDate;
    @Column(name="gross_salary",nullable=false,precision=16,scale=2) private BigDecimal grossSalary;
    @Column(name="net_salary",nullable=false,precision=16,scale=2) private BigDecimal netSalary;
    @Column(name="staff_count",nullable=false) private Long staffCount;
    @Column(name="paid_count",nullable=false) private Long paidCount;
    @Column(name="updated_at",nullable=false) private OffsetDateTime updatedAt;
    @PrePersist @PreUpdate void touch(){updatedAt=OffsetDateTime.now();}
}
