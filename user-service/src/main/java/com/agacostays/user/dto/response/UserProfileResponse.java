package com.agacostays.user.dto.response;
public record UserProfileResponse(Long userId,String fullName,String email,String phone,String roleName,String userType,String userStatus,String address) {}