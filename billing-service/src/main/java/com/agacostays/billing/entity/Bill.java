package com.agacostays.billing.entity;
import com.agacostays.billing.enums.*;import jakarta.persistence.*;import lombok.*;import java.math.BigDecimal;import java.time.OffsetDateTime;
@Entity @Table(name="bills") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Bill{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="bill_id") Long billId;
 @Column(name="branch_id",nullable=false) Long branchId; @Column(name="booking_id",nullable=false,unique=true) Long bookingId; @Column(name="customer_id",nullable=false) Long customerId;
 @Column(name="room_charges",nullable=false,precision=14,scale=2) BigDecimal roomCharges=BigDecimal.ZERO;
 @Column(name="restaurant_charges",nullable=false,precision=14,scale=2) BigDecimal restaurantCharges=BigDecimal.ZERO;
 @Column(name="tax_amount",nullable=false,precision=14,scale=2) BigDecimal taxAmount=BigDecimal.ZERO;
 @Column(name="discount",nullable=false,precision=14,scale=2) BigDecimal discount=BigDecimal.ZERO;
 @Column(name="final_amount",nullable=false,precision=14,scale=2) BigDecimal finalAmount=BigDecimal.ZERO;
 @Enumerated(EnumType.STRING) @Column(name="bill_status",nullable=false) BillStatus billStatus=BillStatus.DRAFT;
 @Enumerated(EnumType.STRING) @Column(name="bill_type",nullable=false) BillType billType=BillType.FINAL_BILL;
 @Column(name="payment_status",nullable=false) String paymentStatus="PENDING"; @Column(name="invoice_url") String invoiceUrl;
 @Column(name="created_at",nullable=false) OffsetDateTime createdAt; @Column(name="updated_at",nullable=false) OffsetDateTime updatedAt;
 @PrePersist void create(){OffsetDateTime n=OffsetDateTime.now();createdAt=n;updatedAt=n;} @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
}
