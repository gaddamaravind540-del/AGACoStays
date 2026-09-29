package com.agacostays.user.dto.response;
public record CustomerResponse(Long customerId,Long userId,String fullName,String email,String phone,String address,String status) {}