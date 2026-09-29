package com.agacostays.support.event;

public record SupportStatusChangedEvent(Long requestId, Long branchId, Long customerId, String status) {}
