package com.agacostays.branch.dto.response;
public record ReceptionistContactResponse(Long contactId,Long branchId,Long staffId,String phone,String alternatePhone,
 String email,String shift,String availableFrom,String availableTo,String purpose,boolean emergencyContact,String status) {}
