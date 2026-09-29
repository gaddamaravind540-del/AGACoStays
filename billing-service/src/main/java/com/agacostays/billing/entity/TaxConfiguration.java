package com.agacostays.billing.entity;
import jakarta.persistence.*;import lombok.*;import java.math.BigDecimal;
@Entity @Table(name="tax_configurations") @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TaxConfiguration{ @Id @GeneratedValue(strategy=GenerationType.IDENTITY) @Column(name="tax_configuration_id") Long taxConfigurationId; @Column(name="branch_id",unique=true) Long branchId; @Column(name="tax_rate",nullable=false,precision=8,scale=4) BigDecimal taxRate; @Column(nullable=false) boolean active=true; }
