package com.agacostays.branch.event;

public record BranchStatusChangedEvent(String eventId,Long branchId,String status,java.time.Instant occurredAt){}
