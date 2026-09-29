package com.agacostays.payroll.entity;
import com.agacostays.payroll.enums.BankAccountStatus; import jakarta.persistence.*; import lombok.*; import java.time.OffsetDateTime;
@Entity @Table(name="staff_bank_accounts") @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class StaffBankAccount{
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="bank_account_id") Long bankAccountId; @Column(nullable=false,unique=true) Long staffId;
 @Column(nullable=false) String accountHolderName; @Column(nullable=false) String bankName; @Column(nullable=false) String accountNumber; @Column(nullable=false) String ifscCode;
 String branchName; String accountType; @Enumerated(EnumType.STRING) @Column(nullable=false) BankAccountStatus status; Long createdBy;
 @Column(nullable=false) OffsetDateTime createdAt; @Column(nullable=false) OffsetDateTime updatedAt;
 @PrePersist void create(){createdAt=updatedAt=OffsetDateTime.now();} @PreUpdate void update(){updatedAt=OffsetDateTime.now();}
}
