package com.agacostays.analytics.dto.response;
import java.time.OffsetDateTime;
public record ExportReportResponse(Long reportId,String reportType,String format,String status,Long branchId,
String fileUrl,OffsetDateTime createdAt,OffsetDateTime completedAt,String errorMessage){}
