package com.agacostays.branch.mapper;
import com.agacostays.branch.dto.response.ReceptionistContactResponse;
import com.agacostays.branch.entity.ReceptionistContact;
import org.springframework.stereotype.Component;
@Component public class ReceptionistContactMapper {
 public ReceptionistContactResponse toResponse(ReceptionistContact c){
  return new ReceptionistContactResponse(c.getContactId(),c.getBranch().getBranchId(),c.getStaffId(),c.getPhone(),c.getAlternatePhone(),c.getEmail(),
   c.getShift(),c.getAvailableFrom()==null?null:c.getAvailableFrom().toString(),c.getAvailableTo()==null?null:c.getAvailableTo().toString(),
   c.getPurpose(),c.isEmergencyContact(),c.getStatus().name());
 }
}
