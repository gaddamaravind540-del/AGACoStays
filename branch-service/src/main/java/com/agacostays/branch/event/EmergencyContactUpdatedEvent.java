package com.agacostays.branch.event;

public record EmergencyContactUpdatedEvent(String eventId,Long branchId,Long contactId,String operation,java.time.Instant occurredAt){}
