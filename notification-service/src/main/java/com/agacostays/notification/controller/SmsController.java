package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.SendSmsRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.SmsResponse;
import com.agacostays.notification.service.SmsService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/notifications")
public class SmsController {
    private final SmsService service;
    public SmsController(SmsService service){this.service=service;}
    @PostMapping("/sms") @PreAuthorize("hasRole('SYSTEM')")
    public ApiResponse<SmsResponse> send(@Valid @RequestBody SendSmsRequest req){return ApiResponse.ok(service.send(req),"SMS processed",null);}
}
