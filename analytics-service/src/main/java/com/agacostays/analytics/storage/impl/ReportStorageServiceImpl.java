package com.agacostays.analytics.storage.impl;

import com.agacostays.analytics.enums.ReportFormat;
import com.agacostays.analytics.exception.ResourceNotFoundException;
import com.agacostays.analytics.storage.ReportStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.*;

@Service
public class ReportStorageServiceImpl implements ReportStorageService {
    private final Path root;
    public ReportStorageServiceImpl(@Value("${analytics.storage.path}")String path){root=Paths.get(path);}
    public String store(Long reportId,ReportFormat format,byte[] data){
        try{
            Files.createDirectories(root);
            String ext=switch(format){case PDF->"pdf";case EXCEL->"xlsx";case CSV->"csv";};
            Files.write(root.resolve(reportId+"."+ext),data,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
            return "/api/analytics/reports/"+reportId+"/download";
        }catch(IOException e){throw new IllegalStateException("Unable to store report",e);}
    }
    public byte[] read(Long reportId,ReportFormat format){
        try{
            String ext=switch(format){case PDF->"pdf";case EXCEL->"xlsx";case CSV->"csv";};
            Path p=root.resolve(reportId+"."+ext);
            if(!Files.exists(p))throw new ResourceNotFoundException("Report file not found");
            return Files.readAllBytes(p);
        }catch(IOException e){throw new IllegalStateException("Unable to read report",e);}
    }
}
