package com.agacostays.branch.service.impl;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import com.agacostays.branch.entity.*;
import com.agacostays.branch.enums.ContactAvailabilityStatus; import com.agacostays.branch.exception.BranchNotFoundException; import com.agacostays.branch.mapper.EmergencyContactMapper;
import com.agacostays.branch.repository.*; import com.agacostays.branch.service.EmergencyContactService; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class EmergencyContactServiceImpl implements EmergencyContactService{
 private final HotelBranchRepository branches;private final EmergencyContactRepository repo;private final EmergencyContactMapper mapper;
 public EmergencyContactServiceImpl(HotelBranchRepository b,EmergencyContactRepository r,EmergencyContactMapper m){branches=b;repo=r;mapper=m;}
 @Transactional public EmergencyContactResponse add(Long branchId,EmergencyContactRequest r,Long actor){
  HotelBranch b=branches.findById(branchId).orElseThrow(()->new BranchNotFoundException("Branch not found: "+branchId));
  EmergencyContact c=repo.save(EmergencyContact.builder().branch(b).contactName(r.contactName()).phone(r.phone()).alternatePhone(r.alternatePhone())
   .email(r.email()).purpose(r.purpose()).status(ContactAvailabilityStatus.AVAILABLE).build());
  return mapper.toResponse(c);
 }
 @Transactional(readOnly=true) public java.util.List<EmergencyContactResponse> list(Long branchId){
  if(!branches.existsById(branchId))throw new BranchNotFoundException("Branch not found: "+branchId);
  return repo.findByBranch_BranchIdOrderByPurposeAsc(branchId).stream().map(mapper::toResponse).toList();
 }
}
