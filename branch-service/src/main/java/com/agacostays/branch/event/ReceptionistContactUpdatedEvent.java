package com.agacostays.branch.event;

public record ReceptionistContactUpdatedEvent(String eventId,Long branchId,Long contactId,String operation,java.time.Instant occurredAt){}
