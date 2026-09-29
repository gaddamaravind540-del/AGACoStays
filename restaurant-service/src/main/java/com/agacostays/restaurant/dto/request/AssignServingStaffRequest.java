package com.agacostays.restaurant.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignServingStaffRequest {
    @NotNull private Long servingStaffId;
}
