package com.agacostays.support.mapper;

import com.agacostays.support.dto.response.SupportRequestResponse;
import com.agacostays.support.entity.CustomerSupportRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerSupportRequestMapper {
    public SupportRequestResponse toResponse(CustomerSupportRequest x) {
        return SupportRequestResponse.builder()
                .requestId(x.getRequestId()).branchId(x.getBranchId()).customerId(x.getCustomerId())
                .bookingId(x.getBookingId()).requestType(x.getRequestType()).subject(x.getSubject())
                .description(x.getDescription()).priority(x.getPriority()).status(x.getStatus())
                .assignedTo(x.getAssignedTo()).createdAt(x.getCreatedAt()).updatedAt(x.getUpdatedAt())
                .build();
    }
}
