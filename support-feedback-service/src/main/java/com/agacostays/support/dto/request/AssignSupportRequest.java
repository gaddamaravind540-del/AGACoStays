package com.agacostays.support.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignSupportRequest {
    @NotNull private Long assignedTo;
}
