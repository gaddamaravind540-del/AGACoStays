package com.agacostays.analytics.service;
import com.agacostays.analytics.dto.request.ExportReportRequest;
import com.agacostays.analytics.dto.response.ExportReportResponse;
public interface ReportExportService {
    ExportReportResponse export(ExportReportRequest request,Long requesterId);
    ExportReportResponse get(Long reportId);
    byte[] download(Long reportId);
}
