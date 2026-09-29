package com.agacostays.user.dto.response;
public record UserResponse(Long userId,String fullName,String email,String phone,String roleName,String userType,String status) {}