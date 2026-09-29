package com.agacostays.attendance.entity;

import com.agacostays.attendance.enums.AttendanceSource;
import com.agacostays.attendance.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;

@Entity
@Table(name="attendance",
    uniqueConstraints=@UniqueConstraint(name="uk_attendance_staff_date", columnNames={"staff_id","attendance_date"}),
    indexes={
        @Index(name="idx_attendance_branch_date", columnList="branch_id,attendance_date"),
        @Index(name="idx_attendance_staff_date", columnList="staff_id,attendance_date")
    })
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="attendance_id")
    private Long attendanceId;

    @Column(name="branch_id", nullable=false)
    private Long branchId;

    @Column(name="staff_id", nullable=false)
    private Long staffId;

    @Column(name="attendance_date", nullable=false)
    private LocalDate attendanceDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=30)
    private AttendanceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name="source", nullable=false, length=30)
    private AttendanceSource source;

    @Enumerated(EnumType.STRING)
    @Column(name="leave_type", nullable=false, length=30)
    private com.agacostays.attendance.enums.LeaveType leaveType;

    @Column(name="check_in_time")
    private LocalTime checkInTime;

    @Column(name="check_out_time")
    private LocalTime checkOutTime;

    @Column(name="worked_hours", precision=8, scale=2)
    private BigDecimal workedHours;

    @Column(name="remarks", length=500)
    private String remarks;

    @Column(name="created_by")
    private Long createdBy;

    @Column(name="updated_by")
    private Long updatedBy;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt;

    @PrePersist
    void onCreate() {
        OffsetDateTime now=OffsetDateTime.now();
        createdAt=now; updatedAt=now;
    }
    @PreUpdate
    void onUpdate() { updatedAt=OffsetDateTime.now(); }
}
