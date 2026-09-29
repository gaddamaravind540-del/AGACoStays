package com.agacostays.branch.event;

public record BranchCreatedEvent(String eventId,Long branchId,String branchName,Long cityId,java.time.Instant occurredAt){}
