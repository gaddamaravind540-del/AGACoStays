package com.agacostays.user.service.impl;

import com.agacostays.user.dto.request.*;
import com.agacostays.user.dto.response.*;
import com.agacostays.user.entity.*;
import com.agacostays.user.enums.*;
import com.agacostays.user.exception.*;
import com.agacostays.user.mapper.ManagerMapper;
import com.agacostays.user.repository.*;
import com.agacostays.user.service.RootAdminService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RootAdminServiceImpl implements RootAdminService {
 private final UserRepository users; private final RoleRepository roles; private final ManagerProfileRepository managers;
 private final PasswordEncoder encoder; private final ManagerMapper mapper; private final UserAuditService audit;
 public RootAdminServiceImpl(UserRepository u,RoleRepository r,ManagerProfileRepository m,PasswordEncoder e,ManagerMapper mm,UserAuditService a){
  users=u;roles=r;managers=m;encoder=e;mapper=mm;audit=a;
 }
 @Override @Transactional public ManagerResponse createManager(CreateManagerRequest r,Long actor){
  if(users.existsByEmailIgnoreCase(r.email())) throw new DuplicateUserException("Email already exists");
  Role role=roles.findByRoleNameIgnoreCase("MANAGER").orElseThrow(()->new RoleNotFoundException("MANAGER role not found"));
  User u=users.save(User.builder().fullName(r.fullName()).email(r.email().trim().toLowerCase()).phone(r.phone()).passwordHash(encoder.encode(r.password()))
    .role(role).userType(UserType.MANAGER).status(UserStatus.ACTIVE).build());
  ManagerProfile p=managers.save(ManagerProfile.builder().user(u).fullName(u.getFullName()).email(u.getEmail()).phone(u.getPhone())
    .accessLevel(r.accessLevel()==null?ManagerAccessLevel.ALL_BRANCHES:r.accessLevel()).status("ACTIVE").createdByRootAdmin(actor).build());
  audit.log(actor,u.getUserId(),"CREATE_MANAGER","Manager created");
  return mapper.toResponse(p);
 }
 @Override @Transactional(readOnly=true) public PageResponse<ManagerResponse> listManagers(int page,int size){
  Page<ManagerProfile> p=managers.findAll(PageRequest.of(Math.max(page,0),Math.min(size,100),Sort.by("managerId").descending()));
  return new PageResponse<>(p.getContent().stream().map(mapper::toResponse).toList(),p.getNumber(),p.getSize(),p.getTotalElements(),p.getTotalPages());
 }
 @Override @Transactional(readOnly=true) public ManagerResponse getManager(Long id){return mapper.toResponse(find(id));}
 @Override @Transactional public ManagerResponse updateManager(Long id,UpdateManagerRequest r,Long actor){
  ManagerProfile p=find(id); p.setFullName(r.fullName());p.setPhone(r.phone());if(r.accessLevel()!=null)p.setAccessLevel(r.accessLevel());
  p.getUser().setFullName(r.fullName());p.getUser().setPhone(r.phone());managers.save(p);audit.log(actor,p.getUser().getUserId(),"UPDATE_MANAGER","Manager updated");return mapper.toResponse(p);
 }
 @Override @Transactional public UserStatusResponse updateManagerStatus(Long id,boolean active,Long actor){
  ManagerProfile p=find(id);p.setStatus(active?"ACTIVE":"INACTIVE");p.getUser().setStatus(active?UserStatus.ACTIVE:UserStatus.INACTIVE);users.save(p.getUser());managers.save(p);
  audit.log(actor,p.getUser().getUserId(),"UPDATE_MANAGER_STATUS",p.getStatus());return new UserStatusResponse(p.getUser().getUserId(),p.getUser().getStatus().name());
 }
 @Override @Transactional public void deleteManager(Long id,Long actor){
  ManagerProfile p=find(id);p.setStatus("INACTIVE");p.getUser().setStatus(UserStatus.INACTIVE);managers.save(p);users.save(p.getUser());audit.log(actor,p.getUser().getUserId(),"DELETE_MANAGER","Manager soft-deleted");
 }
 private ManagerProfile find(Long id){return managers.findById(id).orElseThrow(()->new ManagerNotFoundException("Manager not found: "+id));}
}
