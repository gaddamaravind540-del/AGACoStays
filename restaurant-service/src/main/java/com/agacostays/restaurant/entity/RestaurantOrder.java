package com.agacostays.restaurant.entity;

import com.agacostays.restaurant.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_orders",
       indexes = {@Index(name="idx_order_branch", columnList="branch_id"),
                  @Index(name="idx_order_booking", columnList="booking_id"),
                  @Index(name="idx_order_customer", columnList="customer_id")})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "booking_id")
    private Long bookingId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 30)
    private FoodOrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type", nullable = false, length = 30)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false, length = 30)
    private RestaurantPaymentOption paymentMode;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();
        createdAt = now; updatedAt = now;
    }
    @PreUpdate void onUpdate() { updatedAt = OffsetDateTime.now(); }
}
