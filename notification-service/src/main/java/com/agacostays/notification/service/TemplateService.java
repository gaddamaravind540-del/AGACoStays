package com.agacostays.notification.service;

import com.agacostays.notification.dto.request.TemplateRequest;
import com.agacostays.notification.dto.response.TemplateResponse;

public interface TemplateService {
    TemplateResponse createEmail(TemplateRequest request);
    TemplateResponse updateEmail(Long id, TemplateRequest request);
    TemplateResponse createSms(TemplateRequest request);
}
