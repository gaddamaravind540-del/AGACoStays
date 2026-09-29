package com.agacostays.branch.dto.response;
public record EmergencyContactResponse(Long contactId,Long branchId,String contactName,String phone,String alternatePhone,
 String email,String purpose,String status) {}
