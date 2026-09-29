package com.agacostays.support.dto.request;

import com.agacostays.support.enums.SupportStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSupportStatusRequest {
    @NotNull private SupportStatus status;
    private String remarks;
}
