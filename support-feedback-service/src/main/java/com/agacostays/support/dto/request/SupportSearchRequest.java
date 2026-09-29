package com.agacostays.support.dto.request;

import com.agacostays.support.enums.*;
import lombok.Data;

@Data
public class SupportSearchRequest {
    private SupportStatus status;
    private SupportPriority priority;
    private SupportRequestType requestType;
}
