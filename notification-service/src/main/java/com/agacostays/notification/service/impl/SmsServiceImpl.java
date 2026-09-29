package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.request.SendSmsRequest;
import com.agacostays.notification.dto.response.SmsResponse;
import com.agacostays.notification.entity.NotificationLog;
import com.agacostays.notification.enums.NotificationStatus;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.provider.SmsProvider;
import com.agacostays.notification.repository.NotificationLogRepository;
import com.agacostays.notification.repository.SmsTemplateRepository;
import com.agacostays.notification.service.SmsService;
import com.agacostays.notification.util.NotificationUtil;
import com.agacostays.notification.validation.NotificationValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class SmsServiceImpl implements SmsService {
    private final SmsProvider provider; private final NotificationLogRepository logs; private final SmsTemplateRepository templates; private final NotificationUtil util; private final NotificationValidationService validation;
    public SmsServiceImpl(SmsProvider provider,NotificationLogRepository logs,SmsTemplateRepository templates,NotificationUtil util,NotificationValidationService validation){this.provider=provider;this.logs=logs;this.templates=templates;this.util=util;this.validation=validation;}
    @Override @Transactional public SmsResponse send(SendSmsRequest req){
        validation.validatePhone(req.phone());
        String body=req.message(); var t=templates.findByTemplateCodeAndActiveTrue(req.templateCode()); if(t.isPresent()) body=t.get().getMessage(); body=util.render(body,req.variables());
        var log=new NotificationLog(); log.setBranchId(req.branchId());log.setCustomerId(req.customerId());log.setBookingId(req.bookingId());log.setNotificationType(NotificationType.GENERAL);log.setTitle(req.templateCode());log.setMessage(body);log.setEmailStatus(NotificationStatus.PENDING.name());log=logs.save(log);
        var result=provider.send(req.phone(),body);log.setEmailStatus(result.success()?NotificationStatus.SENT.name():NotificationStatus.FAILED.name());if(result.success())log.setSentAt(LocalDateTime.now());logs.save(log);
        return new SmsResponse(log.getNotificationId(),log.getEmailStatus(),result.detail());
    }
}
