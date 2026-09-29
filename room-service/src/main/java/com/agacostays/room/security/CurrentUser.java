package com.agacostays.room.security;

public record CurrentUser(Long userId, String role, Long branchId) {}
