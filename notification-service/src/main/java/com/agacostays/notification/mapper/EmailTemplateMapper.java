package com.agacostays.notification.mapper;

import com.agacostays.notification.dto.response.TemplateResponse;
import com.agacostays.notification.entity.EmailTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplateMapper {
    public TemplateResponse toResponse(EmailTemplate t){return new TemplateResponse(t.getTemplateId(), t.getTemplateCode(), t.getSubject(), t.getBody(), t.isActive());}
}
