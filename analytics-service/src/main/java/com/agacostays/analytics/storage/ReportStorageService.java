package com.agacostays.analytics.storage;
import com.agacostays.analytics.enums.ReportFormat;
public interface ReportStorageService {
    String store(Long reportId,ReportFormat format,byte[] data);
    byte[] read(Long reportId,ReportFormat format);
}
