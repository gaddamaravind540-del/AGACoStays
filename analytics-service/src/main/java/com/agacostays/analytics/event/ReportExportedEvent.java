package com.agacostays.analytics.event;
public record ReportExportedEvent(Long reportId,String reportType,String format,String status){}
