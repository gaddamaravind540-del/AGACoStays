package com.agacostays.analytics.entity;

import com.agacostays.analytics.enums.ExportStatus;
import com.agacostays.analytics.enums.ReportFormat;
import com.agacostays.analytics.enums.ReportType;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="reports", indexes={
    @Index(name="idx_reports_status", columnList="status"),
    @Index(name="idx_reports_requester", columnList="requested_by")
})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Report {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="report_id") private Long reportId;
    @Enumerated(EnumType.STRING)
    @Column(name="report_type",nullable=false,length=50) private ReportType reportType;
    @Enumerated(EnumType.STRING)
    @Column(name="report_format",nullable=false,length=20) private ReportFormat reportFormat;
    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable=false,length=20) private ExportStatus status;
    @Column(name="branch_id") private Long branchId;
    @Column(name="file_url") private String fileUrl;
    @Column(name="requested_by") private Long requestedBy;
    @Column(name="created_at",nullable=false) private OffsetDateTime createdAt;
    @Column(name="completed_at") private OffsetDateTime completedAt;
    @Column(name="error_message") private String errorMessage;

    @PrePersist
    void onCreate(){createdAt=OffsetDateTime.now();}
}
