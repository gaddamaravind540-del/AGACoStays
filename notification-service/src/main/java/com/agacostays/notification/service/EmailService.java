package com.agacostays.notification.service;

import com.agacostays.notification.dto.request.BookingEmailRequest;
import com.agacostays.notification.dto.request.FeedbackEmailRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.EmailResponse;

public interface EmailService {
    EmailResponse send(SendEmailRequest request);
    EmailResponse sendBookingEmail(BookingEmailRequest request);
    EmailResponse sendFeedbackEmail(FeedbackEmailRequest request);
}
