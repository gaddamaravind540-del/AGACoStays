package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.request.BookingEmailRequest;
import com.agacostays.notification.dto.request.FeedbackEmailRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.service.EmailService;
import com.agacostays.notification.service.NotificationService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {
    private final NotificationService notificationService;
    public EmailServiceImpl(NotificationService notificationService){this.notificationService=notificationService;}
    @Override public EmailResponse send(SendEmailRequest request){return notificationService.sendEmail(request);}
    @Override public EmailResponse sendBookingEmail(BookingEmailRequest r){
        String msg="Customer: {{customerName}}
Booking ID: {{bookingId}}
Hotel: {{hotelBranchName}}
City: {{city}}
Address: {{hotelAddress}}
Map: {{mapLink}}
Receptionist: {{receptionistContact}}
Emergency: {{emergencyContact}}
Room: {{roomNumber}} / {{roomType}}
Description: {{roomDescription}}
Price/Day: {{pricePerDay}}
Check-in: {{checkIn}}
Checkout: {{checkout}}
Payment Status: {{paymentStatus}}
Payment Link: {{paymentLink}}
Total Amount: {{totalAmount}}";
        return notificationService.sendEmail(new SendEmailRequest(r.branchId(),r.customerId(),r.bookingId(),r.email(),"BOOKING_CONFIRMATION","AGA CoStays Booking Confirmation",msg,NotificationType.BOOKING_CONFIRMATION,Map.of("customerName",r.customerName(),"bookingId",r.bookingId(),"hotelBranchName",r.hotelBranchName(),"city",r.city(),"hotelAddress",String.valueOf(r.hotelAddress()),"mapLink",String.valueOf(r.mapLink()),"receptionistContact",String.valueOf(r.receptionistContact()),"emergencyContact",String.valueOf(r.emergencyContact()),"roomNumber",String.valueOf(r.roomNumber()),"roomType",String.valueOf(r.roomType()),"roomDescription",String.valueOf(r.roomDescription()),"pricePerDay",String.valueOf(r.pricePerDay()),"checkIn",String.valueOf(r.checkIn()),"checkout",String.valueOf(r.checkout()),"paymentStatus",String.valueOf(r.paymentStatus()),"paymentLink",String.valueOf(r.paymentLink()),"totalAmount",String.valueOf(r.totalAmount()))));
    }
    @Override public EmailResponse sendFeedbackEmail(FeedbackEmailRequest r){
        String msg="Hello {{customerName}},
Thank you for your feedback.
Type: {{feedbackType}}
Message: {{message}}";
        return notificationService.sendEmail(new SendEmailRequest(r.branchId(),r.customerId(),r.bookingId(),r.email(),"FEEDBACK","AGA CoStays Feedback",msg,NotificationType.FEEDBACK,Map.of("customerName",r.customerName(),"feedbackType",String.valueOf(r.feedbackType()),"message",String.valueOf(r.message()))));
    }
}
