package com.agacostays.user.dto.response;

public record StaffBranchMappingResponse(Long mappingId, Long staffId, Long branchId, String roleName,
		String department, String shift, String assignedFrom, String assignedTo, String status) {
}