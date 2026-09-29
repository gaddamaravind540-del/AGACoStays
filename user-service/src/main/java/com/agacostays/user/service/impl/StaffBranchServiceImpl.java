package com.agacostays.user.service.impl;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.StaffBranchMappingResponse;
import com.agacostays.user.entity.*; import com.agacostays.user.enums.BranchAssignmentStatus;
import com.agacostays.user.exception.*; import com.agacostays.user.mapper.StaffBranchMappingMapper;
import com.agacostays.user.repository.*; import com.agacostays.user.service.StaffBranchService;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
@Service
public class StaffBranchServiceImpl implements StaffBranchService {
 private final StaffRepository staffRepo; private final RoleRepository roleRepo; private final StaffBranchMappingRepository mappingRepo;
 private final StaffBranchMappingMapper mapper; private final UserAuditService audit;
 public StaffBranchServiceImpl(StaffRepository s,RoleRepository r,StaffBranchMappingRepository m,StaffBranchMappingMapper mm,UserAuditService a){staffRepo=s;roleRepo=r;mappingRepo=m;mapper=mm;audit=a;}
 @Transactional public StaffBranchMappingResponse assign(Long staffId,AssignStaffToBranchRequest r,Long actor){
  Staff staff=staffRepo.findById(staffId).orElseThrow(()->new StaffNotFoundException("Staff not found"));
  Role role=roleRepo.findByRoleNameIgnoreCase(r.roleName()).orElseThrow(()->new RoleNotFoundException("Role not found"));
  StaffBranchMapping m=mappingRepo.save(StaffBranchMapping.builder().staff(staff).branchId(r.branchId()).role(role)
     .department(r.department()).shift(r.shift()).assignedFrom(r.assignedFrom()).status(BranchAssignmentStatus.ACTIVE).createdBy(actor).build());
  audit.log(actor,staff.getUser().getUserId(),"ASSIGN_BRANCH","Branch "+r.branchId());return mapper.toResponse(m);
 }
 @Transactional public StaffBranchMappingResponse transfer(Long staffId,TransferBranchRequest r,Long actor){
  Staff staff=staffRepo.findById(staffId).orElseThrow(()->new StaffNotFoundException("Staff not found"));
  mappingRepo.findByStaff_StaffId(staffId).stream().filter(x->x.getStatus()==BranchAssignmentStatus.ACTIVE).forEach(x->{x.setStatus(BranchAssignmentStatus.TRANSFERRED);x.setAssignedTo(r.transferDate().minusDays(1));mappingRepo.save(x);});
  StaffBranchMapping m=mappingRepo.save(StaffBranchMapping.builder().staff(staff).branchId(r.newBranchId()).role(staff.getUser().getRole())
      .department(staff.getDepartment()).shift(staff.getShift()).assignedFrom(r.transferDate()).status(BranchAssignmentStatus.ACTIVE).createdBy(actor).build());
  audit.log(actor,staff.getUser().getUserId(),"TRANSFER_BRANCH","Branch "+r.newBranchId());return mapper.toResponse(m);
 }
}
