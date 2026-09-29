package com.agacostays.support.service;

import com.agacostays.support.dto.request.*;
import com.agacostays.support.dto.response.*;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CustomerSupportService {
    SupportRequestResponse create(CreateSupportRequest request);
    List<SupportRequestResponse> myRequests();
    SupportRequestResponse get(Long requestId);
    SupportRequestResponse updateStatus(Long requestId, UpdateSupportStatusRequest request);
    SupportRequestResponse assign(Long requestId, AssignSupportRequest request);
    PageResponse<SupportRequestResponse> search(Long branchId, Pageable pageable);
}
