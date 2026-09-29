package com.agacostays.branch.entity;

import com.agacostays.branch.enums.ContactAvailabilityStatus;
import com.agacostays.branch.enums.EmergencyContactPurpose;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_contacts", indexes = {
        @Index(name = "idx_emergency_branch", columnList = "branch_id")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmergencyContact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "branch_id", nullable = false)
    private HotelBranch branch;

    @Column(name = "contact_name", nullable = false, length = 150)
    private String contactName;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "alternate_phone", length = 20)
    private String alternatePhone;

    @Column(name = "email", length = 180)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "purpose", nullable = false, length = 30)
    private EmergencyContactPurpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ContactAvailabilityStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
