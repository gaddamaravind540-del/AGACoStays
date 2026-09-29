package com.agacostays.user.dto.response;
public record ManagerResponse(Long managerId,Long userId,String fullName,String email,String phone,String accessLevel,String status) {}