package com.agacostays.payroll.entity;
import com.agacostays.payroll.enums.*; import jakarta.persistence.*; import lombok.*; import java.math.BigDecimal; import java.time.OffsetDateTime;
@Entity @Table(name="payroll_payment_batches") @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class PayrollPaymentBatch{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long paymentBatchId; Long branchId; Integer month; Integer year; Integer totalStaff; BigDecimal totalAmount;
 @Enumerated(EnumType.STRING) PaymentMode paymentMode; @Enumerated(EnumType.STRING) PayrollBatchStatus status; Long createdBy; OffsetDateTime createdAt; OffsetDateTime completedAt;
 @PrePersist void create(){createdAt=OffsetDateTime.now();}
}
