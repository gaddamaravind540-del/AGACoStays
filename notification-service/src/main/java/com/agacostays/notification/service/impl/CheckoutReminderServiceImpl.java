package com.agacostays.notification.service.impl;

import com.agacostays.notification.client.UserServiceClient;
import com.agacostays.notification.dto.request.CheckoutReminderRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.CheckoutReminderResponse;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.entity.CheckoutReminder;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.enums.ReminderStatus;
import com.agacostays.notification.exception.BusinessRuleException;
import com.agacostays.notification.exception.ResourceNotFoundException;
import com.agacostays.notification.mapper.CheckoutReminderMapper;
import com.agacostays.notification.repository.CheckoutReminderRepository;
import com.agacostays.notification.service.CheckoutReminderService;
import com.agacostays.notification.service.NotificationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CheckoutReminderServiceImpl implements CheckoutReminderService {
    private final CheckoutReminderRepository repo;
    private final CheckoutReminderMapper mapper;
    private final NotificationService notifications;
    private final UserServiceClient users;

    public CheckoutReminderServiceImpl(CheckoutReminderRepository repo,CheckoutReminderMapper mapper,NotificationService notifications,UserServiceClient users){this.repo=repo;this.mapper=mapper;this.notifications=notifications;this.users=users;}

    @Override @Transactional public CheckoutReminderResponse schedule(CheckoutReminderRequest r){
        if(r.reminderBeforeHours()<=0) throw new BusinessRuleException("reminderBeforeHours must be greater than zero");
        var x=new CheckoutReminder();x.setBranchId(r.branchId());x.setBookingId(r.bookingId());x.setCustomerId(r.customerId());x.setCheckoutDate(r.checkoutDate());x.setCheckoutTime(r.checkoutTime());x.setReminderBeforeHours(r.reminderBeforeHours());x.setScheduledTime(r.scheduledTime());x.setStatus(ReminderStatus.SCHEDULED);return mapper.toResponse(repo.save(x));
    }
    @Override @Transactional public void cancel(Long id){var x=load(id);if(x.getStatus()==ReminderStatus.SENT)throw new BusinessRuleException("Sent reminder cannot be cancelled");x.setStatus(ReminderStatus.CANCELLED);repo.save(x);}
    @Override @Transactional public void processDueReminders(){
        var due=repo.findByStatusAndScheduledTimeLessThanEqualOrderByScheduledTimeAsc(ReminderStatus.SCHEDULED,LocalDateTime.now(),PageRequest.of(0,50));
        for(var x:due){x.setStatus(ReminderStatus.PROCESSING);repo.save(x);
            String email=users.customerEmail(x.getCustomerId());
            if(email==null){x.setStatus(ReminderStatus.FAILED);repo.save(x);continue;}
            try{var result=sendForBooking(x.getBookingId(),x.getBranchId(),x.getCustomerId(),email);
                if(result!=null && "SENT".equalsIgnoreCase(result.status())){x.setStatus(ReminderStatus.SENT);x.setSentTime(LocalDateTime.now());} else x.setStatus(ReminderStatus.FAILED);
            }catch(Exception ex){x.setStatus(ReminderStatus.FAILED);}repo.save(x);
        }
    }
    @Override public CheckoutReminder load(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Checkout reminder not found: "+id));}
    @Override public CheckoutReminder getForBooking(Long bookingId,Long customerId){
        var list=repo.findByBookingIdOrderByScheduledTimeDesc(bookingId);
        var x=list.stream().filter(r->customerId==null || customerId.equals(r.getCustomerId())).findFirst().orElseThrow(()->new ResourceNotFoundException("Checkout reminder not found for booking: "+bookingId));
        return x;
    }
    @Override public EmailResponse sendForBooking(Long bookingId,Long branchId,Long customerId,String email){
        var latest=getForBooking(bookingId,customerId);
        if(email==null || email.isBlank()) email=users.customerEmail(latest.getCustomerId());
        if(email==null || email.isBlank()) throw new BusinessRuleException("Customer email could not be resolved");
        return notifications.sendEmail(new SendEmailRequest(latest.getBranchId(),latest.getCustomerId(),latest.getBookingId(),email,"CHECKOUT_REMINDER","AGA CoStays Checkout Reminder","Your checkout is scheduled for {{date}} at {{time}}.",NotificationType.CHECKOUT_REMINDER,Map.of("date",latest.getCheckoutDate(),"time",latest.getCheckoutTime())));
    }
}
