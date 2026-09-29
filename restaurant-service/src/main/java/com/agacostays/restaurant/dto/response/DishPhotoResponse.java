package com.agacostays.restaurant.dto.response;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class DishPhotoResponse {
    private Long photoId;
    private Long menuItemId;
    private String photoUrl;
    private String caption;
    private boolean primaryPhoto;
}
