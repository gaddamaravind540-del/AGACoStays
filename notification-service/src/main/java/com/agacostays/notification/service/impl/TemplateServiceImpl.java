package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.request.TemplateRequest;
import com.agacostays.notification.dto.response.TemplateResponse;
import com.agacostays.notification.entity.EmailTemplate;
import com.agacostays.notification.entity.SmsTemplate;
import com.agacostays.notification.exception.DuplicateResourceException;
import com.agacostays.notification.exception.ResourceNotFoundException;
import com.agacostays.notification.mapper.EmailTemplateMapper;
import com.agacostays.notification.mapper.SmsTemplateMapper;
import com.agacostays.notification.repository.EmailTemplateRepository;
import com.agacostays.notification.repository.SmsTemplateRepository;
import com.agacostays.notification.service.TemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final EmailTemplateRepository emails; private final SmsTemplateRepository sms; private final EmailTemplateMapper em; private final SmsTemplateMapper sm;
    public TemplateServiceImpl(EmailTemplateRepository emails,SmsTemplateRepository sms,EmailTemplateMapper em,SmsTemplateMapper sm){this.emails=emails;this.sms=sms;this.em=em;this.sm=sm;}
    @Override @Transactional public TemplateResponse createEmail(TemplateRequest r){if(emails.existsByTemplateCode(r.code()))throw new DuplicateResourceException("Email template already exists: "+r.code());var t=new EmailTemplate();t.setTemplateCode(r.code());t.setSubject(r.subject());t.setBody(r.body());t.setActive(r.active()==null||r.active());return em.toResponse(emails.save(t));}
    @Override @Transactional public TemplateResponse updateEmail(Long id,TemplateRequest r){var t=emails.findById(id).orElseThrow(()->new ResourceNotFoundException("Email template not found: "+id));t.setTemplateCode(r.code());t.setSubject(r.subject());t.setBody(r.body());t.setActive(r.active()==null||r.active());return em.toResponse(emails.save(t));}
    @Override @Transactional public TemplateResponse createSms(TemplateRequest r){if(sms.existsByTemplateCode(r.code()))throw new DuplicateResourceException("SMS template already exists: "+r.code());var t=new SmsTemplate();t.setTemplateCode(r.code());t.setMessage(r.body());t.setActive(r.active()==null||r.active());return sm.toResponse(sms.save(t));}
}
