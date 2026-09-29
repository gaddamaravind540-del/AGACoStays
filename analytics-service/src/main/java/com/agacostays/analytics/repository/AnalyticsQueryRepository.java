package com.agacostays.analytics.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class AnalyticsQueryRepository {
    @PersistenceContext private EntityManager em;

    public BigDecimal revenueTotal(){
        Object v=em.createNativeQuery("select coalesce(sum(total_revenue),0) from hotel_revenue_projection").getSingleResult();
        return new BigDecimal(v.toString());
    }
    public long bookingTotal(){
        Object v=em.createNativeQuery("select coalesce(sum(booking_count),0) from hotel_revenue_projection").getSingleResult();
        return ((Number)v).longValue();
    }
    public long customerTotal(){
        Object v=em.createNativeQuery("select coalesce(sum(customer_count),0) from hotel_revenue_projection").getSingleResult();
        return ((Number)v).longValue();
    }
    public long branchCount(){
        Object v=em.createNativeQuery("select count(distinct branch_id) from hotel_revenue_projection").getSingleResult();
        return ((Number)v).longValue();
    }
}
