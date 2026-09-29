package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.request.ExportReportRequest;
import com.agacostays.analytics.dto.response.ExportReportResponse;
import com.agacostays.analytics.entity.Report;
import com.agacostays.analytics.enums.*;
import com.agacostays.analytics.exception.ResourceNotFoundException;
import com.agacostays.analytics.mapper.ReportMapper;
import com.agacostays.analytics.repository.ReportRepository;
import com.agacostays.analytics.service.*;
import com.agacostays.analytics.storage.ReportStorageService;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class ReportExportServiceImpl implements ReportExportService {
    private final ReportRepository repo;
    private final ReportMapper mapper;
    private final ReportStorageService storage;
    private final HotelAnalyticsService hotel;
    private final RestaurantAnalyticsService restaurant;
    private final ManagerAnalyticsService manager;
    private final RootAdminAnalyticsService root;

    public ReportExportServiceImpl(ReportRepository repo,ReportMapper mapper,ReportStorageService storage,
                                   HotelAnalyticsService hotel,RestaurantAnalyticsService restaurant,
                                   ManagerAnalyticsService manager,RootAdminAnalyticsService root){
        this.repo=repo;this.mapper=mapper;this.storage=storage;this.hotel=hotel;this.restaurant=restaurant;
        this.manager=manager;this.root=root;
    }

    @Override @Transactional
    public ExportReportResponse export(ExportReportRequest request,Long requesterId){
        Report r=Report.builder().reportType(request.reportType()).reportFormat(request.format())
            .status(ExportStatus.PENDING).branchId(request.branchId()).requestedBy(requesterId).build();
        r=repo.save(r);
        try{
            String text=buildSummary(request);
            byte[] data=switch(request.format()){
                case CSV -> csv(text);
                case EXCEL -> excel(text);
                case PDF -> pdf(text);
            };
            r.setFileUrl(storage.store(r.getReportId(),request.format(),data));
            r.setStatus(ExportStatus.COMPLETED);r.setCompletedAt(OffsetDateTime.now());repo.save(r);
        }catch(Exception e){
            r.setStatus(ExportStatus.FAILED);r.setErrorMessage(e.getMessage());r.setCompletedAt(OffsetDateTime.now());repo.save(r);
        }
        return mapper.toResponse(r);
    }

    @Override public ExportReportResponse get(Long reportId){
        return mapper.toResponse(repo.findById(reportId).orElseThrow(()->new ResourceNotFoundException("Report not found: "+reportId)));
    }

    @Override public byte[] download(Long reportId){
        Report r=repo.findById(reportId).orElseThrow(()->new ResourceNotFoundException("Report not found: "+reportId));
        return storage.read(reportId,r.getReportFormat());
    }

    private String buildSummary(ExportReportRequest req){
        return switch(req.reportType()){
            case HOTEL_DASHBOARD -> {
                var x=hotel.dashboard(req.branchId());
                yield "metric,value\nrevenue,"+x.totalRevenue()+"\nroomRevenue,"+x.roomRevenue()+"\nrestaurantRevenue,"+x.restaurantRevenue()
                    +"\noccupancyRate,"+x.occupancyRate()+"\nbookings,"+x.bookings()+"\ncustomers,"+x.customers();
            }
            case RESTAURANT_DASHBOARD -> {
                var x=restaurant.dashboard(req.branchId());
                yield "metric,value\nsales,"+x.totalSales()+"\norders,"+x.orders()+"\ndelivered,"+x.deliveredOrders()+"\nrating,"+x.averageRating();
            }
            case BRANCH_PERFORMANCE, WEBSITE, REVENUE, OCCUPANCY, ATTENDANCE, PAYROLL -> {
                var x=manager.dashboard();
                yield "metric,value\nrevenue,"+x.totalRevenue()+"\nbookings,"+x.totalBookings()+"\noccupancyRate,"+x.occupancyRate()
                    +"\nattendancePresent,"+x.attendancePresent()+"\nattendanceAbsent,"+x.attendanceAbsent()+"\npayrollNet,"+x.payrollNet();
            }
        };
    }

    private byte[] csv(String text){return text.getBytes(StandardCharsets.UTF_8);}

    private byte[] excel(String text) throws IOException{
        try(XSSFWorkbook wb=new XSSFWorkbook();ByteArrayOutputStream out=new ByteArrayOutputStream()){
            var sheet=wb.createSheet("Report");
            int row=0;
            for(String line:text.split("\\R")){
                Row r=sheet.createRow(row++);
                String[] cells=line.split(",",-1);
                for(int i=0;i<cells.length;i++) r.createCell(i).setCellValue(cells[i]);
            }
            wb.write(out);return out.toByteArray();
        }
    }

    private byte[] pdf(String text) throws IOException{
        try(PDDocument doc=new PDDocument();ByteArrayOutputStream out=new ByteArrayOutputStream()){
            PDPage page=new PDPage(PDRectangle.A4);doc.addPage(page);
            try(PDPageContentStream cs=new PDPageContentStream(doc,page)){
                cs.beginText();cs.setFont(new org.apache.pdfbox.pdmodel.font.PDType1Font(org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName.HELVETICA),11);
                cs.newLineAtOffset(45,780);
                for(String line:text.split("\\R")){
                    cs.showText(line.length()>110?line.substring(0,110):line);cs.newLineAtOffset(0,-16);
                }
                cs.endText();
            }
            doc.save(out);return out.toByteArray();
        }
    }
}
