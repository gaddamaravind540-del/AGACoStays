package com.agacostays.attendance.entity;

import com.agacostays.attendance.enums.AttendanceCorrectionStatus;
import com.agacostays.attendance.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name="attendance_corrections", indexes={
    @Index(name="idx_corrections_attendance", columnList="attendance_id"),
    @Index(name="idx_corrections_staff", columnList="staff_id")
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class AttendanceCorrection {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="correction_id")
    private Long correctionId;

    @Column(name="attendance_id", nullable=false)
    private Long attendanceId;

    @Column(name="staff_id", nullable=false)
    private Long staffId;

    @Column(name="attendance_date", nullable=false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name="requested_status", length=30)
    private AttendanceStatus requestedStatus;

    @Column(name="requested_check_in")
    private LocalTime requestedCheckIn;

    @Column(name="requested_check_out")
    private LocalTime requestedCheckOut;

    @Column(name="reason", nullable=false, length=500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=30)
    private AttendanceCorrectionStatus status;

    @Column(name="requested_by", nullable=false)
    private Long requestedBy;

    @Column(name="approved_by")
    private Long approvedBy;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() { createdAt=OffsetDateTime.now(); }
}
