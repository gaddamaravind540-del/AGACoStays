package com.agacostays.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "restaurant_order_items",
       indexes = {@Index(name="idx_order_item_order", columnList="order_id")})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantOrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long orderItemId;

    @Column(name="order_id", nullable=false)
    private Long orderId;

    @Column(name="menu_item_id", nullable=false)
    private Long menuItemId;

    @Column(name="item_name", nullable=false)
    private String itemName;

    @Column(nullable=false)
    private Integer quantity;

    @Column(name="unit_price", nullable=false, precision=12, scale=2)
    private BigDecimal unitPrice;

    @Column(nullable=false, precision=12, scale=2)
    private BigDecimal lineTotal;
}
