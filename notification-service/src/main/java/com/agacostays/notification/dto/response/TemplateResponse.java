package com.agacostays.notification.dto.response;

public record TemplateResponse(Long templateId, String code, String subject, String body, boolean active) {}
