package com.agacostays.branch.service.impl;
import com.agacostays.branch.dto.request.*; import com.agacostays.branch.dto.response.*; import com.agacostays.branch.entity.*;
import com.agacostays.branch.enums.ContactAvailabilityStatus; import com.agacostays.branch.exception.BranchNotFoundException; import com.agacostays.branch.mapper.ReceptionistContactMapper;
import com.agacostays.branch.repository.*; import com.agacostays.branch.service.ReceptionistContactService; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service public class ReceptionistContactServiceImpl implements ReceptionistContactService{
 private final HotelBranchRepository branches;private final ReceptionistContactRepository repo;private final ReceptionistContactMapper mapper;
 public ReceptionistContactServiceImpl(HotelBranchRepository b,ReceptionistContactRepository r,ReceptionistContactMapper m){branches=b;repo=r;mapper=m;}
 @Transactional public ReceptionistContactResponse add(Long branchId,ReceptionistContactRequest r,Long actor){
  HotelBranch b=branches.findById(branchId).orElseThrow(()->new BranchNotFoundException("Branch not found: "+branchId));
  ReceptionistContact c=repo.save(ReceptionistContact.builder().branch(b).staffId(r.staffId()).phone(r.phone()).alternatePhone(r.alternatePhone()).email(r.email())
   .shift(r.shift()).availableFrom(r.availableFrom()).availableTo(r.availableTo()).purpose(r.purpose()).emergencyContact(false).status(ContactAvailabilityStatus.AVAILABLE).build());
  return mapper.toResponse(c);
 }
 @Transactional(readOnly=true) public java.util.List<ReceptionistContactResponse> list(Long branchId){
  if(!branches.existsById(branchId))throw new BranchNotFoundException("Branch not found: "+branchId);
  return repo.findByBranch_BranchIdOrderByShiftAsc(branchId).stream().map(mapper::toResponse).toList();
 }
}
