package com.agacostays.payroll.entity;
import com.agacostays.payroll.enums.SalarySlipStatus; import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="salary_slips") @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SalarySlip{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long salarySlipId; @Column(nullable=false,unique=true) Long payrollId; Long staffId; Integer month; Integer year;
 @Column(nullable=false) String salarySlipUrl; @Column(nullable=false) OffsetDateTime generatedAt; @Enumerated(EnumType.STRING) @Column(nullable=false) SalarySlipStatus sentEmailStatus;
 @Column(nullable=false) OffsetDateTime createdAt; @PrePersist void create(){OffsetDateTime n=OffsetDateTime.now();generatedAt=n;createdAt=n;}
}
