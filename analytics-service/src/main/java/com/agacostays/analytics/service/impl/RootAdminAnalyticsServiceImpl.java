package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.repository.HotelRevenueProjectionRepository;
import com.agacostays.analytics.service.RootAdminAnalyticsService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class RootAdminAnalyticsServiceImpl implements RootAdminAnalyticsService {
    private final HotelRevenueProjectionRepository repo;
    public RootAdminAnalyticsServiceImpl(HotelRevenueProjectionRepository repo){this.repo=repo;}
    public RootDashboardResponse dashboard(){
        LocalDate to=LocalDate.now(),from=to.minusDays(30);
        var rows=repo.findByMetricDateBetween(from,to);
        BigDecimal revenue=rows.stream().map(x->x.getTotalRevenue()==null?BigDecimal.ZERO:x.getTotalRevenue()).reduce(BigDecimal.ZERO,BigDecimal::add);
        long bookings=rows.stream().mapToLong(x->x.getBookingCount()==null?0:x.getBookingCount()).sum();
        long customers=rows.stream().mapToLong(x->x.getCustomerCount()==null?0:x.getCustomerCount()).sum();
        Map<Long,BigDecimal> byBranch=new LinkedHashMap<>();
        rows.forEach(x->byBranch.merge(x.getBranchId(),x.getTotalRevenue()==null?BigDecimal.ZERO:x.getTotalRevenue(),BigDecimal::add));
        List<BranchRevenueResponse> branchRevenue=byBranch.entrySet().stream()
            .map(e->new BranchRevenueResponse(e.getKey(),e.getValue(),"LAST_30_DAYS")).toList();
        return new RootDashboardResponse(revenue,byBranch.size(),bookings,customers,branchRevenue);
    }
}
