package com.agacostays.payroll.entity;
import com.agacostays.payroll.enums.SalaryStructureStatus; import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.*;
@Entity @Table(name="salary_structures") @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SalaryStructure{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="salary_structure_id") Long salaryStructureId;
 @Column(name="role_id") Long roleId; @Column(name="role_name",nullable=false) String roleName;
 @Column(name="basic_salary",nullable=false,precision=14,scale=2) BigDecimal basicSalary;
 @Column(name="hra",nullable=false,precision=14,scale=2) BigDecimal hra;
 @Column(name="allowances",nullable=false,precision=14,scale=2) BigDecimal allowances;
 @Column(name="deductions",nullable=false,precision=14,scale=2) BigDecimal deductions;
 @Column(name="monthly_gross_salary",nullable=false,precision=14,scale=2) BigDecimal monthlyGrossSalary;
 @Column(name="monthly_net_salary",nullable=false,precision=14,scale=2) BigDecimal monthlyNetSalary;
 @Column(name="currency",nullable=false,length=10) String currency; @Column(name="effective_from",nullable=false) LocalDate effectiveFrom;
 @Enumerated(EnumType.STRING) @Column(name="status",nullable=false,length=20) SalaryStructureStatus status; Long createdBy;
 @Column(nullable=false) OffsetDateTime createdAt; @Column(nullable=false) OffsetDateTime updatedAt;
 @PrePersist void create(){createdAt=updatedAt=OffsetDateTime.now();} @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
}
