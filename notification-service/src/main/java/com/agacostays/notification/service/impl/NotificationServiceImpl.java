package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.dto.response.NotificationResponse;
import com.agacostays.notification.entity.NotificationLog;
import com.agacostays.notification.enums.NotificationStatus;
import com.agacostays.notification.mapper.NotificationLogMapper;
import com.agacostays.notification.repository.NotificationLogRepository;
import com.agacostays.notification.service.NotificationService;
import com.agacostays.notification.provider.EmailProvider;
import com.agacostays.notification.util.NotificationUtil;
import com.agacostays.notification.validation.NotificationValidationService;
import com.agacostays.notification.event.NotificationFailedEvent;
import com.agacostays.notification.event.NotificationSentEvent;
import com.agacostays.notification.producer.NotificationEventProducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationLogRepository repo; private final NotificationLogMapper mapper; private final EmailProvider provider; private final NotificationUtil util; private final NotificationValidationService validation; private final NotificationEventProducer events;
    public NotificationServiceImpl(NotificationLogRepository repo, NotificationLogMapper mapper, EmailProvider provider, NotificationUtil util, NotificationValidationService validation, NotificationEventProducer events){this.repo=repo;this.mapper=mapper;this.provider=provider;this.util=util;this.validation=validation;this.events=events;}

    @Override @Transactional
    public EmailResponse sendEmail(SendEmailRequest req){
        validation.validateEmail(req.to());
        var log=new NotificationLog(); log.setBranchId(req.branchId()); log.setCustomerId(req.customerId()); log.setBookingId(req.bookingId());
        log.setNotificationType(req.notificationType()==null?com.agacostays.notification.enums.NotificationType.GENERAL:req.notificationType());
        log.setTitle(req.subject()==null?req.templateCode():req.subject()); log.setMessage(util.render(req.message(),req.variables())); log.setEmailTo(req.to()); log.setEmailStatus(NotificationStatus.PENDING.name()); log=repo.save(log);
        var result=provider.send(req.to(), log.getTitle(), log.getMessage());
        log.setEmailStatus(result.success()?NotificationStatus.SENT.name():NotificationStatus.FAILED.name());
        if(result.success()) log.setSentAt(LocalDateTime.now()); repo.save(log);
        if(result.success()) events.sent(new NotificationSentEvent(log.getNotificationId(),log.getCustomerId(),log.getBookingId(),log.getNotificationType().name())); else events.failed(new NotificationFailedEvent(log.getNotificationId(), result.detail()));
        return new EmailResponse(log.getNotificationId(), log.getEmailStatus(), result.detail());
    }
    @Override public Page<NotificationResponse> myNotifications(Long customerId, Pageable pageable){return repo.findByCustomerIdOrderByCreatedAtDesc(customerId,pageable).map(mapper::toResponse);}
    @Override @Transactional public NotificationResponse markRead(Long id, Long customerId, boolean rootAdmin){
        var log=repo.findById(id).orElseThrow(()->new com.agacostays.notification.exception.ResourceNotFoundException("Notification not found: "+id));
        if(!rootAdmin && (customerId==null || !customerId.equals(log.getCustomerId()))) throw new com.agacostays.notification.exception.AccessDeniedException("Notification belongs to another customer");
        log.setRead(true); return mapper.toResponse(repo.save(log));
    }
}
