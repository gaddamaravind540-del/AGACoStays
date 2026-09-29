package com.agacostays.payroll.entity;
import com.agacostays.payroll.enums.*; import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="payroll",uniqueConstraints=@UniqueConstraint(name="uk_payroll_staff_period",columnNames={"staff_id","month","year"}))
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Payroll{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="payroll_id") Long payrollId; Long branchId; Long staffId; Long roleId; String department;
 Integer month; Integer year; Integer totalWorkingDays; Integer presentDays; Integer absentDays; Integer halfDays; Integer leaveDays;
 BigDecimal basicSalary; BigDecimal hra; BigDecimal allowances; BigDecimal bonus; BigDecimal grossSalary; BigDecimal attendanceDeduction; BigDecimal otherDeductions; BigDecimal netSalary;
 @Enumerated(EnumType.STRING) @Column(name="payment_status",nullable=false) PayrollStatus paymentStatus;
 @Enumerated(EnumType.STRING) @Column(name="payment_mode",nullable=false) PaymentMode paymentMode;
 String transactionId; OffsetDateTime paidAt; String remarks; Long generatedBy; @Column(nullable=false) OffsetDateTime createdAt; @Column(nullable=false) OffsetDateTime updatedAt;
 @PrePersist void create(){createdAt=updatedAt=OffsetDateTime.now();} @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
}
