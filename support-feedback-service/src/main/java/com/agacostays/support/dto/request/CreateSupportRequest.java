package com.agacostays.support.dto.request;

import com.agacostays.support.enums.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateSupportRequest {
    private Long branchId;
    private Long bookingId;
    @NotNull private SupportRequestType requestType;
    @NotBlank private String subject;
    @NotBlank private String description;
    @NotNull private SupportPriority priority;
}
