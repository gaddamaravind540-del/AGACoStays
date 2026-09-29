package com.agacostays.analytics.mapper;
import com.agacostays.analytics.dto.response.ExportReportResponse;
import com.agacostays.analytics.entity.Report;
import org.springframework.stereotype.Component;
@Component
public class ReportMapper {
    public ExportReportResponse toResponse(Report x){
        return new ExportReportResponse(x.getReportId(),x.getReportType().name(),x.getReportFormat().name(),
            x.getStatus().name(),x.getBranchId(),x.getFileUrl(),x.getCreatedAt(),x.getCompletedAt(),x.getErrorMessage());
    }
}
