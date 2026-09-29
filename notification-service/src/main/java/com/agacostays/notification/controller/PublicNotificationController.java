package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.BookingEmailRequest;
import com.agacostays.notification.dto.request.CheckoutReminderRequest;
import com.agacostays.notification.dto.request.CheckoutReminderSendRequest;
import com.agacostays.notification.dto.request.FeedbackEmailRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.CheckoutReminderResponse;
import com.agacostays.notification.dto.response.EmailPreviewResponse;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.mapper.CheckoutReminderMapper;
import com.agacostays.notification.repository.EmailTemplateRepository;
import com.agacostays.notification.service.CheckoutReminderService;
import com.agacostays.notification.service.EmailService;
import com.agacostays.notification.security.CurrentUserProvider;
import com.agacostays.notification.exception.AccessDeniedException;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class PublicNotificationController {
    private final EmailService emailService;
    private final CheckoutReminderService reminderService;
    private final EmailTemplateRepository templates;
    private final CheckoutReminderMapper reminderMapper;
    private final CurrentUserProvider current;

    public PublicNotificationController(EmailService emailService,CheckoutReminderService reminderService,EmailTemplateRepository templates,CheckoutReminderMapper reminderMapper,CurrentUserProvider current){this.emailService=emailService;this.reminderService=reminderService;this.templates=templates;this.reminderMapper=reminderMapper;this.current=current;}

    @PostMapping("/booking-confirmation-email") @PreAuthorize("hasAnyRole('SYSTEM','RECEPTIONIST','MANAGER')")
    public ApiResponse<EmailResponse> bookingConfirmation(@Valid @RequestBody BookingEmailRequest request){return ApiResponse.ok(emailService.sendBookingEmail(request),"Booking confirmation processed",null);}

    @GetMapping("/booking/{bookingId}/email-preview") @PreAuthorize("hasAnyRole('CUSTOMER','RECEPTIONIST','MANAGER')")
    public ApiResponse<EmailPreviewResponse> preview(@PathVariable Long bookingId){
        var t=templates.findByTemplateCodeAndActiveTrue("BOOKING_CONFIRMATION").orElse(null);
        if(t==null) return ApiResponse.ok(new EmailPreviewResponse(bookingId,"BOOKING_CONFIRMATION","AGA CoStays Booking Confirmation","Booking confirmation template is not configured."),"Preview created",null);
        return ApiResponse.ok(new EmailPreviewResponse(bookingId,t.getTemplateCode(),t.getSubject(),t.getBody()),"Preview created",null);
    }

    @PostMapping("/booking/{bookingId}/resend-email") @PreAuthorize("hasAnyRole('CUSTOMER','RECEPTIONIST','MANAGER')")
    public ApiResponse<EmailResponse> resend(@PathVariable Long bookingId,@Valid @RequestBody BookingEmailRequest request){
        if(request.bookingId()!=null && !bookingId.equals(request.bookingId())) throw new com.agacostays.notification.exception.BusinessRuleException("Path bookingId and request bookingId must match");
        String role=current.role();
        boolean privileged=role!=null && (role.contains("RECEPTIONIST")||role.contains("MANAGER")||role.contains("ROOT_ADMIN"));
        if(!privileged){Long customer=current.customerId();if(customer==null || request.customerId()==null || !customer.equals(request.customerId())) throw new AccessDeniedException("Customer can resend only their own booking notification");}
        return ApiResponse.ok(emailService.sendBookingEmail(request),"Booking email resent",null);
    }

    @PostMapping("/checkout-reminder/schedule") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<CheckoutReminderResponse> schedule(@Valid @RequestBody CheckoutReminderRequest request){return ApiResponse.ok(reminderService.schedule(request),"Checkout reminder scheduled",null);}

    @PostMapping("/checkout-reminder/send") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> sendReminder(@Valid @RequestBody CheckoutReminderSendRequest request){return ApiResponse.ok(reminderService.sendForBooking(request.bookingId(),request.branchId(),request.customerId(),request.email()),"Checkout reminder sent",null);}

    @GetMapping("/checkout-reminder/booking/{bookingId}") @PreAuthorize("hasRole('CUSTOMER')")
    public ApiResponse<CheckoutReminderResponse> getReminder(@PathVariable Long bookingId){
        var customer=current.customerId(); if(customer==null) throw new AccessDeniedException("Customer identity is required");
        return ApiResponse.ok(reminderMapper.toResponse(reminderService.getForBooking(bookingId,customer)),"Checkout reminder loaded",null);
    }

    @PostMapping("/feedback-email") @PreAuthorize("hasAnyRole('SYSTEM','RECEPTIONIST','MANAGER')")
    public ApiResponse<EmailResponse> feedback(@Valid @RequestBody FeedbackEmailRequest request){return ApiResponse.ok(emailService.sendFeedbackEmail(request),"Feedback email processed",null);}

    @PostMapping("/payment-confirmation") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> payment(@Valid @RequestBody SendEmailRequest request){return ApiResponse.ok(emailService.send(request),"Payment notification processed",null);}
}
