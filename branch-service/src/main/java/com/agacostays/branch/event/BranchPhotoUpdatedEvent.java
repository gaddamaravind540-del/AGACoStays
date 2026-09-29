package com.agacostays.branch.event;

public record BranchPhotoUpdatedEvent(String eventId,Long branchId,Long photoId,String operation,java.time.Instant occurredAt){}
