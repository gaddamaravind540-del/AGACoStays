package com.agacostays.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "restaurant_menu_photos",
       indexes = {@Index(name="idx_menu_photo_item", columnList="menu_item_id")})
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RestaurantMenuPhoto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    private String caption;

    @Column(name = "is_primary", nullable = false)
    private boolean primaryPhoto;

    @Column(name = "uploaded_by")
    private Long uploadedBy;

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
