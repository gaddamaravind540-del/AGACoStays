package com.agacostays.notification.controller;

import com.agacostays.notification.dto.request.TemplateRequest;
import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.TemplateResponse;
import com.agacostays.notification.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/root-admin/templates")
public class TemplateController {
    private final TemplateService service;
    public TemplateController(TemplateService service){this.service=service;}
    @PostMapping("/email") @PreAuthorize("hasRole('ROOT_ADMIN')")
    public ApiResponse<TemplateResponse> createEmail(@Valid @RequestBody TemplateRequest req){return ApiResponse.ok(service.createEmail(req),"Email template created",null);}
    @PutMapping("/email/{templateId}") @PreAuthorize("hasRole('ROOT_ADMIN')")
    public ApiResponse<TemplateResponse> updateEmail(@PathVariable Long templateId,@Valid @RequestBody TemplateRequest req){return ApiResponse.ok(service.updateEmail(templateId,req),"Email template updated",null);}
    @PostMapping("/sms") @PreAuthorize("hasRole('ROOT_ADMIN')")
    public ApiResponse<TemplateResponse> createSms(@Valid @RequestBody TemplateRequest req){return ApiResponse.ok(service.createSms(req),"SMS template created",null);}
}
