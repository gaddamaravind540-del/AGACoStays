package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.entity.*;
import com.agacostays.analytics.repository.*;
import com.agacostays.analytics.service.ManagerAnalyticsService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ManagerAnalyticsServiceImpl implements ManagerAnalyticsService {
    private final HotelRevenueProjectionRepository revenue;
    private final AttendanceProjectionRepository attendance;
    private final PayrollProjectionRepository payroll;
    public ManagerAnalyticsServiceImpl(HotelRevenueProjectionRepository revenue,AttendanceProjectionRepository attendance,
                                       PayrollProjectionRepository payroll){
        this.revenue=revenue;this.attendance=attendance;this.payroll=payroll;
    }
    public ManagerDashboardResponse dashboard(){
        LocalDate to=LocalDate.now(),from=to.minusDays(30);
        var rev=revenue.findByMetricDateBetween(from,to);
        BigDecimal total=rev.stream().map(x->x.getTotalRevenue()==null?BigDecimal.ZERO:x.getTotalRevenue()).reduce(BigDecimal.ZERO,BigDecimal::add);
        long bookings=rev.stream().mapToLong(x->x.getBookingCount()==null?0:x.getBookingCount()).sum();
        long occupied=rev.stream().mapToLong(x->x.getOccupiedRoomCount()==null?0:x.getOccupiedRoomCount()).sum();
        double occupancy=Math.min(100,occupied/(double)Math.max(1,rev.size()));
        long present=attendance.findByMetricDateBetween(from,to).stream().mapToLong(x->x.getPresentCount()==null?0:x.getPresentCount()).sum();
        long absent=attendance.findByMetricDateBetween(from,to).stream().mapToLong(x->x.getAbsentCount()==null?0:x.getAbsentCount()).sum();
        BigDecimal net=payroll.findByMetricDateBetween(from,to).stream().map(x->x.getNetSalary()==null?BigDecimal.ZERO:x.getNetSalary()).reduce(BigDecimal.ZERO,BigDecimal::add);
        Map<Long,BigDecimal> map=new LinkedHashMap<>();
        rev.forEach(x->map.merge(x.getBranchId(),x.getTotalRevenue()==null?BigDecimal.ZERO:x.getTotalRevenue(),BigDecimal::add));
        List<BranchRevenueResponse> branches=map.entrySet().stream().map(e->new BranchRevenueResponse(e.getKey(),e.getValue(),"LAST_30_DAYS")).toList();
        return new ManagerDashboardResponse(total,bookings,occupancy,present,absent,net,branches);
    }
}
