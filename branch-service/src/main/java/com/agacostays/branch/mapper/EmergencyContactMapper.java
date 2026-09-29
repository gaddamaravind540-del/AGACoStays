package com.agacostays.branch.mapper;
import com.agacostays.branch.dto.response.EmergencyContactResponse;
import com.agacostays.branch.entity.EmergencyContact;
import org.springframework.stereotype.Component;
@Component public class EmergencyContactMapper {
 public EmergencyContactResponse toResponse(EmergencyContact c){
  return new EmergencyContactResponse(c.getContactId(),c.getBranch().getBranchId(),c.getContactName(),c.getPhone(),c.getAlternatePhone(),c.getEmail(),
   c.getPurpose().name(),c.getStatus().name());
 }
}
