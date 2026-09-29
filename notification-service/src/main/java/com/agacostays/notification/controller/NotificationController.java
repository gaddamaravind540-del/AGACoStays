package com.agacostays.notification.controller;

import com.agacostays.notification.dto.response.ApiResponse;
import com.agacostays.notification.dto.response.NotificationResponse;
import com.agacostays.notification.dto.response.PageResponse;
import com.agacostays.notification.service.NotificationLogService;
import com.agacostays.notification.service.NotificationService;
import com.agacostays.notification.security.CurrentUserProvider;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {
    private final NotificationService notifications; private final NotificationLogService logs; private final CurrentUserProvider current;
    public NotificationController(NotificationService notifications,NotificationLogService logs,CurrentUserProvider current){this.notifications=notifications;this.logs=logs;this.current=current;}
    @GetMapping("/notifications/my-notifications") @PreAuthorize("isAuthenticated()")
    public ApiResponse<PageResponse<NotificationResponse>> my(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        var customerId=current.customerId(); if(customerId==null) throw new com.agacostays.notification.exception.AccessDeniedException("Customer identity is required");
        var p=PageRequest.of(Math.max(page,0),Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt"));
        return ApiResponse.ok(PageResponse.of(notifications.myNotifications(customerId,p)),"Notifications loaded",null);
    }
    @PutMapping("/notifications/{notificationId}/read") @PreAuthorize("isAuthenticated()")
    public ApiResponse<NotificationResponse> read(@PathVariable Long notificationId){return ApiResponse.ok(notifications.markRead(notificationId,current.customerId(),current.role()!=null && current.role().contains("ROOT_ADMIN")),"Notification marked read",null);}
    @GetMapping("/root-admin/notifications/logs") @PreAuthorize("hasRole('ROOT_ADMIN')")
    public ApiResponse<PageResponse<NotificationResponse>> logs(@RequestParam(required=false) Long branchId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="20") int size){
        var p=PageRequest.of(Math.max(page,0),Math.min(size,100),Sort.by(Sort.Direction.DESC,"createdAt")); return ApiResponse.ok(PageResponse.of(logs.search(branchId,p)),"Notification logs loaded",null);
    }
}
