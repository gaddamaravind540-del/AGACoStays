package com.agacostays.analytics.controller;

import com.agacostays.analytics.dto.request.ExportReportRequest;
import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.security.CurrentUserProvider;
import com.agacostays.analytics.service.ReportExportService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analytics/reports")
public class ReportExportController {
    private final ReportExportService service; private final CurrentUserProvider current;
    public ReportExportController(ReportExportService service,CurrentUserProvider current){
        this.service=service;this.current=current;
    }
    @PostMapping("/export")
    public ResponseEntity<ApiResponse<ExportReportResponse>> export(@Valid @RequestBody ExportReportRequest r){
        return ResponseEntity.ok(ApiResponse.ok("Report exported",service.export(r,current.userId())));
    }
    @GetMapping("/{reportId}")
    public ResponseEntity<ApiResponse<ExportReportResponse>> get(@PathVariable Long reportId){
        return ResponseEntity.ok(ApiResponse.ok("Report fetched",service.get(reportId)));
    }
    @GetMapping("/{reportId}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long reportId){
        ExportReportResponse r=service.get(reportId);
        MediaType mt=switch(r.format()){
            case "PDF"->MediaType.APPLICATION_PDF;
            case "EXCEL"->MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            default->MediaType.TEXT_PLAIN;
        };
        return ResponseEntity.ok().contentType(mt)
            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=analytics-report-"+reportId)
            .body(service.download(reportId));
    }
}
