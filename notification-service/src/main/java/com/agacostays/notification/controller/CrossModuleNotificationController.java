package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.SendEmailRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.EmailResponse;
import com.agacostays.notification.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class CrossModuleNotificationController {
    private final EmailService emailService;
    public CrossModuleNotificationController(EmailService emailService){this.emailService=emailService;}

    @PostMapping("/api/restaurant/notifications/order-confirmation-email") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> orderConfirmation(@Valid @RequestBody SendEmailRequest request){return ApiResponse.ok(emailService.send(request),"Restaurant order email processed",null);}

    @PostMapping("/api/restaurant/notifications/food-delivered") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<EmailResponse> foodDelivered(@Valid @RequestBody SendEmailRequest request){return ApiResponse.ok(emailService.send(request),"Food delivered notification processed",null);}

    @PostMapping("/api/manager/payroll/notifications/salary-credited") @PreAuthorize("hasAnyRole('SYSTEM','MANAGER')")
    public ApiResponse<EmailResponse> salaryCredited(@Valid @RequestBody SendEmailRequest request){return ApiResponse.ok(emailService.send(request),"Salary notification processed",null);}
}
