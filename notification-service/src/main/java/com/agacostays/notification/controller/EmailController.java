package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.BookingEmailRequest;
import com.agacostays.notification.dto.request.FeedbackEmailRequest;
import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/notifications")
public class EmailController {
    private final EmailService service;
    public EmailController(EmailService service){this.service=service;}
    @PostMapping("/email") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> send(@Valid @RequestBody SendEmailRequest req,@RequestHeader(value="X-Trace-Id",required=false) String traceId){return ApiResponse.ok(service.send(req),"Email processed",traceId);}
    @PostMapping("/booking-email") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> booking(@Valid @RequestBody BookingEmailRequest req,@RequestHeader(value="X-Trace-Id",required=false) String traceId){return ApiResponse.ok(service.sendBookingEmail(req),"Booking email processed",traceId);}
    @PostMapping("/feedback-email") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> feedback(@Valid @RequestBody FeedbackEmailRequest req,@RequestHeader(value="X-Trace-Id",required=false) String traceId){return ApiResponse.ok(service.sendFeedbackEmail(req),"Feedback email processed",traceId);}
}
