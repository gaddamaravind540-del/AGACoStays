package com.agacostays.restaurant.entity;

import com.agacostays.restaurant.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_menu",
       indexes = {@Index(name="idx_menu_branch", columnList="branch_id"),
                  @Index(name="idx_menu_restaurant", columnList="restaurant_id")})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Long menuItemId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MenuCategory category;

    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean availability;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type", nullable = false, length = 20)
    private FoodType foodType;

    @Column(name = "preparation_time_minutes")
    private Integer preparationTimeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MenuItemStatus status;

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
