package com.agacostays.support.entity;

import com.agacostays.support.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "support_assignments",
       indexes = @Index(name = "idx_assignment_request", columnList = "request_id"))
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class SupportAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @Column(name = "assigned_to", nullable = false)
    private Long assignedTo;

    @Column(name = "assigned_by", nullable = false)
    private Long assignedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_status", nullable = false, length = 30)
    private AssignmentStatus assignmentStatus;

    @Column(name = "assigned_at", nullable = false)
    private OffsetDateTime assignedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @PrePersist
    void onCreate() {
        if (assignedAt == null) assignedAt = OffsetDateTime.now();
    }
}
