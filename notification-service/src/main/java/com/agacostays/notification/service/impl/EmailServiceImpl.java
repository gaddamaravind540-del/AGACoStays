package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.request.BookingEmailRequest;
import com.agacostays.notification.dto.request.FeedbackEmailRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.enums.NotificationType;
import com.agacostays.notification.service.EmailService;
import com.agacostays.notification.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final NotificationService notificationService;

    public EmailServiceImpl(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public EmailResponse send(SendEmailRequest request) {
        return notificationService.sendEmail(request);
    }

    @Override
    public EmailResponse sendBookingEmail(BookingEmailRequest r) {

        String msg =
                "Customer: {{customerName}}\n" +
                "Booking ID: {{bookingId}}\n" +
                "Hotel: {{hotelBranchName}}\n" +
                "City: {{city}}\n" +
                "Address: {{hotelAddress}}\n" +
                "Map: {{mapLink}}\n" +
                "Receptionist: {{receptionistContact}}\n" +
                "Emergency: {{emergencyContact}}\n" +
                "Room: {{roomNumber}} / {{roomType}}\n" +
                "Description: {{roomDescription}}\n" +
                "Price/Day: {{pricePerDay}}\n" +
                "Check-in: {{checkIn}}\n" +
                "Checkout: {{checkout}}\n" +
                "Payment Status: {{paymentStatus}}\n" +
                "Payment Link: {{paymentLink}}\n" +
                "Total Amount: {{totalAmount}}";

        Map<String, Object> variables = new HashMap<>();

        variables.put("customerName", r.customerName());
        variables.put("bookingId", r.bookingId());
        variables.put("hotelBranchName", r.hotelBranchName());
        variables.put("city", r.city());
        variables.put("hotelAddress", r.hotelAddress());
        variables.put("mapLink", r.mapLink());
        variables.put("receptionistContact", r.receptionistContact());
        variables.put("emergencyContact", r.emergencyContact());
        variables.put("roomNumber", r.roomNumber());
        variables.put("roomType", r.roomType());
        variables.put("roomDescription", r.roomDescription());
        variables.put("pricePerDay", r.pricePerDay());
        variables.put("checkIn", r.checkIn());
        variables.put("checkout", r.checkout());
        variables.put("paymentStatus", r.paymentStatus());
        variables.put("paymentLink", r.paymentLink());
        variables.put("totalAmount", r.totalAmount());

        SendEmailRequest request = new SendEmailRequest(
                r.branchId(),
                r.customerId(),
                r.bookingId(),
                r.email(),
                "BOOKING_CONFIRMATION",
                "AGA CoStays Booking Confirmation",
                msg,
                NotificationType.BOOKING_CONFIRMATION,
                variables
        );

        return notificationService.sendEmail(request);
    }

    @Override
    public EmailResponse sendFeedbackEmail(FeedbackEmailRequest r) {

        String msg =
                "Hello {{customerName}},\n\n" +
                "Thank you for your feedback.\n\n" +
                "Type: {{feedbackType}}\n" +
                "Message: {{message}}";

        Map<String, Object> variables = new HashMap<>();

        variables.put("customerName", r.customerName());
        variables.put("feedbackType", r.feedbackType());
        variables.put("message", r.message());

        SendEmailRequest request = new SendEmailRequest(
                r.branchId(),
                r.customerId(),
                r.bookingId(),
                r.email(),
                "FEEDBACK",
                "AGA CoStays Feedback",
                msg,
                NotificationType.FEEDBACK,
                variables
        );

        return notificationService.sendEmail(request);
    }
}