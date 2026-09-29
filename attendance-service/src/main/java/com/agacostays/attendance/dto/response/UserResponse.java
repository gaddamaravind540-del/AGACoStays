package com.agacostays.attendance.dto.response;
public record UserResponse(Long userId, String fullName, String role, Long branchId, boolean active) {}
