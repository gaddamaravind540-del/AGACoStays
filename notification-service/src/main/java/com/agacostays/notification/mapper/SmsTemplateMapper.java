package com.agacostays.notification.mapper;

import com.agacostays.notification.dto.response.TemplateResponse;
import com.agacostays.notification.entity.SmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SmsTemplateMapper {
    public TemplateResponse toResponse(SmsTemplate t){return new TemplateResponse(t.getTemplateId(), t.getTemplateCode(), null, t.getMessage(), t.isActive());}
}
