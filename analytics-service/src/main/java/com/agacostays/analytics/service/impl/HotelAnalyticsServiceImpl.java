package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.dto.response.*;
import com.agacostays.analytics.entity.*;
import com.agacostays.analytics.repository.*;
import com.agacostays.analytics.service.HotelAnalyticsService;
import org.springframework.stereotype.Service;
import java.math.*;
import java.time.LocalDate;
import java.util.*;

@Service
public class HotelAnalyticsServiceImpl implements HotelAnalyticsService {
    private final HotelRevenueProjectionRepository hotel;
    private final FeedbackProjectionRepository feedback;
    public HotelAnalyticsServiceImpl(HotelRevenueProjectionRepository hotel,FeedbackProjectionRepository feedback){
        this.hotel=hotel;this.feedback=feedback;
    }
    public HotelDashboardResponse dashboard(Long branchId){
        LocalDate to=LocalDate.now(),from=to.minusDays(30);
        List<HotelRevenueProjection> rows=branchId==null?hotel.findByMetricDateBetween(from,to):hotel.findByBranchIdAndMetricDateBetween(branchId,from,to);
        BigDecimal room=rows.stream().map(HotelRevenueProjection::getRoomRevenue).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal rest=rows.stream().map(HotelRevenueProjection::getRestaurantRevenue).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal total=rows.stream().map(HotelRevenueProjection::getTotalRevenue).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        long bookings=rows.stream().mapToLong(x->x.getBookingCount()==null?0:x.getBookingCount()).sum();
        long customers=rows.stream().mapToLong(x->x.getCustomerCount()==null?0:x.getCustomerCount()).sum();
        long occupied=rows.stream().mapToLong(x->x.getOccupiedRoomCount()==null?0:x.getOccupiedRoomCount()).sum();
        double occupancy=rows.isEmpty()?0:Math.min(100.0,occupied/(double)Math.max(1,rows.size()*1));
        double rating=feedbackRating(branchId,from,to);

        List<ChartDataResponse> chart=rows.stream().sorted(Comparator.comparing(HotelRevenueProjection::getMetricDate))
            .map(x->new ChartDataResponse(x.getMetricDate().toString(),x.getTotalRevenue())).toList();

        List<KpiResponse> kpis=List.of(
            new KpiResponse("Revenue",total,"30-day projection"),
            new KpiResponse("Bookings",bookings,"30-day projection"),
            new KpiResponse("Customers",customers,"30-day projection"),
            new KpiResponse("Occupancy %",BigDecimal.valueOf(occupancy),"projection")
        );
        return new HotelDashboardResponse(branchId,total,room,rest,BigDecimal.valueOf(occupancy),bookings,customers,rating,kpis,chart);
    }
    private double feedbackRating(Long b,LocalDate f,LocalDate t){
        var rows=b==null?feedback.findByMetricDateBetween(f,t):feedback.findByBranchIdAndMetricDateBetween(b,f,t);
        BigDecimal sum=rows.stream().map(FeedbackProjection::getHotelRatingSum).filter(Objects::nonNull).reduce(BigDecimal.ZERO,BigDecimal::add);
        long c=rows.stream().mapToLong(x->x.getFeedbackCount()==null?0:x.getFeedbackCount()).sum();
        return c==0?0:sum.doubleValue()/c;
    }
}
