package com.agacostays.notification.dto.response;

public record EmailPreviewResponse(Long bookingId, String templateCode, String subject, String body) {}
