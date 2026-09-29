package com.agacostays.analytics.event.listener;

import com.agacostays.analytics.service.AnalyticsProjectionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component @Slf4j
public class AnalyticsKafkaEventListener {
    private final ObjectMapper mapper;
    private final AnalyticsProjectionService projection;
    public AnalyticsKafkaEventListener(ObjectMapper mapper,AnalyticsProjectionService projection){
        this.mapper=mapper;this.projection=projection;
    }

    @KafkaListener(topics={"booking.events","payment.events","restaurant.events","attendance.events","payroll.events","support.events"},
                   groupId="analytics-service")
    public void consume(String message){
        try{
            JsonNode n=mapper.readTree(message);
            Long branchId=longVal(n,"branchId");
            BigDecimal amount=decimalVal(n,"amount");
            if(n.has("roomCharges"))amount=decimalVal(n,"roomCharges");
            String status=text(n,"status");
            String event=text(n,"eventType");
            if(event==null)event=text(n,"type");

            String raw=message.toLowerCase();
            if(raw.contains("booking"))projection.bookingEvent(branchId,amount,longVal(n,"customerId"));
            else if(raw.contains("payment"))projection.paymentEvent(branchId,amount);
            else if(raw.contains("food")||raw.contains("restaurant")||raw.contains("order"))
                projection.restaurantEvent(branchId,amount,"DELIVERED".equalsIgnoreCase(status));
            else if(raw.contains("attendance")||raw.contains("checkin")||raw.contains("checked"))
                projection.attendanceEvent(branchId,status);
            else if(raw.contains("payroll")||raw.contains("salary"))
                projection.payrollEvent(branchId,decimalVal(n,"grossSalary"),decimalVal(n,"netSalary"),
                    "PAID".equalsIgnoreCase(status)||"SUCCESS".equalsIgnoreCase(status));
            else if(raw.contains("feedback"))
                projection.feedbackEvent(branchId,doubleVal(n,"hotelRating"),doubleVal(n,"restaurantRating"),raw.contains("restaurant"));
        }catch(Exception e){log.error("Analytics event processing failed",e);}
    }
    private Long longVal(JsonNode n,String k){return n.hasNonNull(k)?n.get(k).asLong():null;}
    private BigDecimal decimalVal(JsonNode n,String k){return n.hasNonNull(k)?new BigDecimal(n.get(k).asText()):BigDecimal.ZERO;}
    private String text(JsonNode n,String k){return n.hasNonNull(k)?n.get(k).asText():null;}
    private double doubleVal(JsonNode n,String k){return n.hasNonNull(k)?n.get(k).asDouble():0;}
}
