package com.agacostays.branch.event;

public record BranchUpdatedEvent(String eventId,Long branchId,String branchName,java.time.Instant occurredAt){}
