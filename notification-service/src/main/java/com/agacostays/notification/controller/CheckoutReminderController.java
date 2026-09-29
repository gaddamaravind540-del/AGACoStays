package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.CheckoutReminderRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.CheckoutReminderResponse;
import com.agacostays.notification.service.CheckoutReminderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/checkout-reminders")
public class CheckoutReminderController {
    private final CheckoutReminderService service;
    public CheckoutReminderController(CheckoutReminderService service){this.service=service;}
    @PostMapping @PreAuthorize("hasAnyRole('SYSTEM','BOOKING_SERVICE','ROOT_ADMIN')")
    public ApiResponse<CheckoutReminderResponse> schedule(@Valid @RequestBody CheckoutReminderRequest req){return ApiResponse.ok(service.schedule(req),"Checkout reminder scheduled",null);}
    @DeleteMapping("/{reminderId}") @PreAuthorize("hasAnyRole('SYSTEM','BOOKING_SERVICE','ROOT_ADMIN')")
    public ApiResponse<Void> cancel(@PathVariable Long reminderId){service.cancel(reminderId);return ApiResponse.ok(null,"Checkout reminder cancelled",null);}
}
