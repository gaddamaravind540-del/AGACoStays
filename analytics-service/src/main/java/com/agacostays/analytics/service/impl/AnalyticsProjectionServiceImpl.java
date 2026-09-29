package com.agacostays.analytics.service.impl;

import com.agacostays.analytics.entity.*;
import com.agacostays.analytics.repository.*;
import com.agacostays.analytics.service.AnalyticsProjectionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class AnalyticsProjectionServiceImpl implements AnalyticsProjectionService {
    private final HotelRevenueProjectionRepository hotel;
    private final RestaurantSalesProjectionRepository restaurant;
    private final AttendanceProjectionRepository attendance;
    private final PayrollProjectionRepository payroll;
    private final FeedbackProjectionRepository feedback;

    public AnalyticsProjectionServiceImpl(HotelRevenueProjectionRepository hotel,
        RestaurantSalesProjectionRepository restaurant,AttendanceProjectionRepository attendance,
        PayrollProjectionRepository payroll,FeedbackProjectionRepository feedback){
        this.hotel=hotel;this.restaurant=restaurant;this.attendance=attendance;this.payroll=payroll;this.feedback=feedback;
    }

    @Override @Transactional
    public void bookingEvent(Long branchId,BigDecimal roomRevenue,Long customerId){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        HotelRevenueProjection x=hotel.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->HotelRevenueProjection.builder()
            .branchId(branchId).metricDate(d).roomRevenue(BigDecimal.ZERO).restaurantRevenue(BigDecimal.ZERO)
            .totalRevenue(BigDecimal.ZERO).bookingCount(0L).occupiedRoomCount(0L).customerCount(0L).build());
        x.setRoomRevenue(nvl(x.getRoomRevenue()).add(nvl(roomRevenue)));
        x.setTotalRevenue(nvl(x.getRoomRevenue()).add(nvl(x.getRestaurantRevenue())));
        x.setBookingCount(x.getBookingCount()+1);
        if(customerId!=null)x.setCustomerCount(x.getCustomerCount()+1);
        hotel.save(x);
    }

    @Override @Transactional
    public void paymentEvent(Long branchId,BigDecimal amount){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        HotelRevenueProjection x=hotel.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->HotelRevenueProjection.builder()
            .branchId(branchId).metricDate(d).roomRevenue(BigDecimal.ZERO).restaurantRevenue(BigDecimal.ZERO)
            .totalRevenue(BigDecimal.ZERO).bookingCount(0L).occupiedRoomCount(0L).customerCount(0L).build());
        x.setTotalRevenue(nvl(x.getTotalRevenue()).add(nvl(amount)));
        hotel.save(x);
    }

    @Override @Transactional
    public void restaurantEvent(Long branchId,BigDecimal amount,boolean delivered){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        RestaurantSalesProjection x=restaurant.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->RestaurantSalesProjection.builder()
            .branchId(branchId).metricDate(d).salesAmount(BigDecimal.ZERO).orderCount(0L).deliveredCount(0L)
            .chefPerformanceCount(0L).servingPerformanceCount(0L).build());
        x.setSalesAmount(nvl(x.getSalesAmount()).add(nvl(amount)));
        x.setOrderCount(x.getOrderCount()+1);
        if(delivered)x.setDeliveredCount(x.getDeliveredCount()+1);
        restaurant.save(x);

        HotelRevenueProjection h=hotel.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->HotelRevenueProjection.builder()
            .branchId(branchId).metricDate(d).roomRevenue(BigDecimal.ZERO).restaurantRevenue(BigDecimal.ZERO)
            .totalRevenue(BigDecimal.ZERO).bookingCount(0L).occupiedRoomCount(0L).customerCount(0L).build());
        h.setRestaurantRevenue(nvl(h.getRestaurantRevenue()).add(nvl(amount)));
        h.setTotalRevenue(nvl(h.getRoomRevenue()).add(nvl(h.getRestaurantRevenue())));
        hotel.save(h);
    }

    @Override @Transactional
    public void attendanceEvent(Long branchId,String status){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        AttendanceProjection x=attendance.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->AttendanceProjection.builder()
            .branchId(branchId).metricDate(d).presentCount(0L).absentCount(0L).halfDayCount(0L).leaveCount(0L).totalStaff(0L).build());
        String s=status==null?"":status.toUpperCase();
        switch(s){
            case "PRESENT" -> x.setPresentCount(x.getPresentCount()+1);
            case "ABSENT" -> x.setAbsentCount(x.getAbsentCount()+1);
            case "HALF_DAY" -> x.setHalfDayCount(x.getHalfDayCount()+1);
            case "ON_LEAVE" -> x.setLeaveCount(x.getLeaveCount()+1);
            default -> {}
        }
        x.setTotalStaff(x.getPresentCount()+x.getAbsentCount()+x.getHalfDayCount()+x.getLeaveCount());
        attendance.save(x);
    }

    @Override @Transactional
    public void payrollEvent(Long branchId,BigDecimal gross,BigDecimal net,boolean paid){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        PayrollProjection x=payroll.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->PayrollProjection.builder()
            .branchId(branchId).metricDate(d).grossSalary(BigDecimal.ZERO).netSalary(BigDecimal.ZERO)
            .staffCount(0L).paidCount(0L).build());
        x.setGrossSalary(nvl(x.getGrossSalary()).add(nvl(gross)));
        x.setNetSalary(nvl(x.getNetSalary()).add(nvl(net)));
        x.setStaffCount(x.getStaffCount()+1);
        if(paid)x.setPaidCount(x.getPaidCount()+1);
        payroll.save(x);
    }

    @Override @Transactional
    public void feedbackEvent(Long branchId,double hotelRating,double restaurantRating,boolean isRestaurant){
        if(branchId==null)return;
        LocalDate d=LocalDate.now();
        FeedbackProjection x=feedback.findByBranchIdAndMetricDate(branchId,d).orElseGet(()->FeedbackProjection.builder()
            .branchId(branchId).metricDate(d).hotelRatingSum(BigDecimal.ZERO).restaurantRatingSum(BigDecimal.ZERO)
            .feedbackCount(0L).restaurantFeedbackCount(0L).build());
        if(hotelRating>0){x.setHotelRatingSum(x.getHotelRatingSum().add(BigDecimal.valueOf(hotelRating)));x.setFeedbackCount(x.getFeedbackCount()+1);}
        if(restaurantRating>0){x.setRestaurantRatingSum(x.getRestaurantRatingSum().add(BigDecimal.valueOf(restaurantRating)));}
        if(isRestaurant)x.setRestaurantFeedbackCount(x.getRestaurantFeedbackCount()+1);
        feedback.save(x);
    }

    private BigDecimal nvl(BigDecimal x){return x==null?BigDecimal.ZERO:x;}
}
