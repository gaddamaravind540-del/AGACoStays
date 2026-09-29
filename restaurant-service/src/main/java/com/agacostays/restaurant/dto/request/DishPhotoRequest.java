package com.agacostays.restaurant.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DishPhotoRequest {
    @NotBlank private String photoUrl;
    private String caption;
    private boolean primaryPhoto;
}
