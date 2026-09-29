package com.agacostays.user.service.impl;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*;
import com.agacostays.user.entity.*; import com.agacostays.user.enums.RoleStatus;
import com.agacostays.user.exception.*; import com.agacostays.user.mapper.*;
import com.agacostays.user.repository.*; import com.agacostays.user.service.RoleService;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
 private final RoleRepository roleRepo; private final PermissionRepository permissionRepo; private final RolePermissionRepository rpRepo;
 private final RoleMapper roleMapper; private final RolePermissionMapper rpMapper; private final UserAuditService audit;
 public RoleServiceImpl(RoleRepository rr,PermissionRepository pr,RolePermissionRepository rpr,RoleMapper rm,RolePermissionMapper rpm,UserAuditService a){
  roleRepo=rr;permissionRepo=pr;rpRepo=rpr;roleMapper=rm;rpMapper=rpm;audit=a;
 }
 @Transactional public RoleResponse createRole(CreateRoleRequest r,Long actor){
  if(roleRepo.findByRoleNameIgnoreCase(r.roleName()).isPresent()) throw new DuplicateRoleException("Role already exists");
  Role role=roleRepo.save(Role.builder().roleName(r.roleName().trim().toUpperCase()).description(r.description()).status(RoleStatus.ACTIVE).build());
  audit.log(actor,null,"CREATE_ROLE",role.getRoleName()); return roleMapper.toResponse(role);
 }
 @Transactional(readOnly=true) public List<RoleResponse> listRoles(){return roleRepo.findAll().stream().map(roleMapper::toResponse).toList();}
 @Transactional public RoleResponse updateRole(Long id,UpdateRoleRequest r,Long actor){
  Role role=roleRepo.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found: "+id));
  role.setDescription(r.description());role.setStatus(RoleStatus.valueOf(r.status().toUpperCase()));
  roleRepo.save(role);audit.log(actor,null,"UPDATE_ROLE",role.getRoleName());return roleMapper.toResponse(role);
 }
 @Transactional public void deleteRole(Long id,Long actor){
  Role role=roleRepo.findById(id).orElseThrow(()->new RoleNotFoundException("Role not found: "+id));
  if(List.of("ROOT_ADMIN","MANAGER","CUSTOMER","RECEPTIONIST","RESTAURANT_ADMIN","CHEF","SERVING_STAFF","HOUSEKEEPING_STAFF").contains(role.getRoleName()))
   throw new InvalidRoleException("Default role cannot be deleted");
  role.setStatus(RoleStatus.INACTIVE);roleRepo.save(role);audit.log(actor,null,"DELETE_ROLE",role.getRoleName());
 }
 @Transactional public RolePermissionResponse assignPermission(Long roleId,AssignPermissionRequest r,Long actor){
  Role role=roleRepo.findById(roleId).orElseThrow(()->new RoleNotFoundException("Role not found"));
  Permission p=permissionRepo.findById(r.permissionId()).orElseThrow(()->new PermissionNotFoundException("Permission not found"));
  if(rpRepo.existsByRole_RoleIdAndPermission_PermissionId(roleId,p.getPermissionId())) throw new DuplicatePermissionException("Permission already assigned");
  RolePermission rp=rpRepo.save(RolePermission.builder().role(role).permission(p).createdBy(actor).build());
  audit.log(actor,null,"ASSIGN_PERMISSION","Assigned "+p.getPermissionCode()+" to "+role.getRoleName());
  return rpMapper.toResponse(rp);
 }
 @Transactional(readOnly=true) public List<RolePermissionResponse> permissions(Long roleId){
  if(!roleRepo.existsById(roleId)) throw new RoleNotFoundException("Role not found");
  return rpRepo.findByRole_RoleId(roleId).stream().map(rpMapper::toResponse).toList();
 }
}
