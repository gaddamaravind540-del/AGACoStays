package com.agacostays.user.service.impl;

import com.agacostays.user.client.BranchServiceClient;
import com.agacostays.user.client.NotificationServiceClient;
import com.agacostays.user.dto.request.*;
import com.agacostays.user.dto.response.*;
import com.agacostays.user.entity.*;
import com.agacostays.user.enums.*;
import com.agacostays.user.exception.*;
import com.agacostays.user.mapper.CustomerMapper;
import com.agacostays.user.mapper.StaffBranchMappingMapper;
import com.agacostays.user.mapper.StaffMapper;
import com.agacostays.user.repository.*;
import com.agacostays.user.security.BranchAccessValidator;
import com.agacostays.user.service.ManagerService;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final UserRepository users;
    private final RoleRepository roles;
    private final StaffRepository staffRepo;
    private final CustomerRepository customerRepo;
    private final StaffBranchMappingRepository mappingRepo;
    private final PasswordEncoder encoder;
    private final StaffMapper staffMapper;
    private final CustomerMapper customerMapper;
    private final StaffBranchMappingMapper mappingMapper;
    private final BranchServiceClient branchClient;
    private final NotificationServiceClient notificationClient;
    private final UserAuditService audit;
    private final BranchAccessValidator branchAccess;

    public ManagerServiceImpl(
            UserRepository users, RoleRepository roles, StaffRepository staffRepo,
            CustomerRepository customerRepo, StaffBranchMappingRepository mappingRepo,
            PasswordEncoder encoder, StaffMapper staffMapper, CustomerMapper customerMapper,
            StaffBranchMappingMapper mappingMapper, BranchServiceClient branchClient,
            NotificationServiceClient notificationClient, UserAuditService audit,
            BranchAccessValidator branchAccess) {
        this.users=users;this.roles=roles;this.staffRepo=staffRepo;this.customerRepo=customerRepo;
        this.mappingRepo=mappingRepo;this.encoder=encoder;this.staffMapper=staffMapper;
        this.customerMapper=customerMapper;this.mappingMapper=mappingMapper;this.branchClient=branchClient;
        this.notificationClient=notificationClient;this.audit=audit;this.branchAccess=branchAccess;
    }

    @Override
    @Transactional
    public StaffResponse createStaff(CreateStaffRequest r, Long actor) {
        if(users.existsByEmailIgnoreCase(r.email())) throw new DuplicateUserException("Email already exists");
        Role role=roles.findByRoleNameIgnoreCase(r.roleName()).orElseThrow(()->new RoleNotFoundException("Role not found: "+r.roleName()));
        if("ROOT_ADMIN".equalsIgnoreCase(role.getRoleName()) || "MANAGER".equalsIgnoreCase(role.getRoleName()) || "CUSTOMER".equalsIgnoreCase(role.getRoleName()))
            throw new InvalidRoleException("Staff cannot use role "+role.getRoleName());

        User u=users.save(User.builder()
                .fullName(r.fullName()).email(r.email().trim().toLowerCase()).phone(r.phone())
                .passwordHash(encoder.encode(r.password())).role(role)
                .userType(UserType.STAFF).status(UserStatus.ACTIVE).build());

        Staff s=staffRepo.save(Staff.builder().user(u).department(r.department()).shift(r.shift())
                .joiningDate(r.joiningDate()).status(StaffStatus.ACTIVE).build());

        audit.log(actor,u.getUserId(),"CREATE_STAFF","Staff account created");
        return staffMapper.toResponse(s,role.getRoleName());
    }

    @Override
    @Transactional(readOnly=true)
    public PageResponse<StaffResponse> listStaff(int page,int size){
        Page<Staff> p=staffRepo.findAll(PageRequest.of(Math.max(0,page),Math.min(100,Math.max(1,size)),Sort.by("staffId").descending()));
        return new PageResponse<>(p.getContent().stream()
                .map(s -> {
                    String role=s.getUser().getRole().getRoleName();
                    return staffMapper.toResponse(s,role);
                }).toList(),p.getNumber(),p.getSize(),p.getTotalElements(),p.getTotalPages());
    }

    @Override
    @Transactional(readOnly=true)
    public StaffResponse getStaff(Long id){
        Staff s=findStaff(id);
        return staffMapper.toResponse(s,s.getUser().getRole().getRoleName());
    }

    @Override
    @Transactional
    public StaffResponse updateStaff(Long id,UpdateStaffRequest r,Long actor){
        Staff s=findStaff(id);
        s.getUser().setFullName(r.fullName());
        s.getUser().setPhone(r.phone());
        s.setDepartment(r.department());
        s.setShift(r.shift());
        users.save(s.getUser());
        staffRepo.save(s);
        audit.log(actor,s.getUser().getUserId(),"UPDATE_STAFF","Staff updated");
        return staffMapper.toResponse(s,s.getUser().getRole().getRoleName());
    }

    @Override
    @Transactional
    public StaffResponse assignRole(Long id,AssignRoleRequest r,Long actor){
        Staff s=findStaff(id);
        Role role=roles.findByRoleNameIgnoreCase(r.roleName()).orElseThrow(()->new RoleNotFoundException("Role not found: "+r.roleName()));
        if("ROOT_ADMIN".equalsIgnoreCase(role.getRoleName()) || "MANAGER".equalsIgnoreCase(role.getRoleName()) || "CUSTOMER".equalsIgnoreCase(role.getRoleName()))
            throw new InvalidRoleException("Invalid staff role: "+role.getRoleName());
        s.getUser().setRole(role);
        users.save(s.getUser());
        audit.log(actor,s.getUser().getUserId(),"ASSIGN_STAFF_ROLE",role.getRoleName());
        return staffMapper.toResponse(s,role.getRoleName());
    }

    @Override
    @Transactional
    public UserStatusResponse updateStaffStatus(Long id,boolean active,Long actor){
        Staff s=findStaff(id);
        s.setStatus(active?StaffStatus.ACTIVE:StaffStatus.INACTIVE);
        s.getUser().setStatus(active?UserStatus.ACTIVE:UserStatus.INACTIVE);
        staffRepo.save(s);users.save(s.getUser());
        audit.log(actor,s.getUser().getUserId(),"UPDATE_STAFF_STATUS",s.getStatus().name());
        return new UserStatusResponse(s.getUser().getUserId(),s.getUser().getStatus().name());
    }

    @Override
    @Transactional
    public StaffBranchMappingResponse assignStaffToBranch(Long staffId,AssignStaffToBranchRequest r,Long actor){
        Staff s=findStaff(staffId);
        Role role=roles.findByRoleNameIgnoreCase(r.roleName()).orElseThrow(()->new RoleNotFoundException("Role not found"));
        if(!branchClient.branchExists(r.branchId())) throw new BranchMappingException("Branch does not exist: "+r.branchId());

        StaffBranchMapping m=StaffBranchMapping.builder()
                .staff(s).branchId(r.branchId()).role(role).department(r.department())
                .shift(r.shift()).assignedFrom(r.assignedFrom()).status(BranchAssignmentStatus.ACTIVE)
                .createdBy(actor).build();
        m=mappingRepo.save(m);
        notificationClient.sendStaffAssignedNotification(s.getUser().getEmail(),String.valueOf(r.branchId()));
        audit.log(actor,s.getUser().getUserId(),"ASSIGN_STAFF_BRANCH","Branch "+r.branchId()+" assigned");
        return mappingMapper.toResponse(m);
    }

    @Override
    @Transactional
    public StaffBranchMappingResponse transferBranch(Long staffId,TransferBranchRequest r,Long actor){
        Staff s=findStaff(staffId);
        if(!branchClient.branchExists(r.newBranchId())) throw new BranchMappingException("Branch does not exist: "+r.newBranchId());
        var mappings=mappingRepo.findByStaff_StaffId(staffId);
        mappings.stream().filter(m->m.getStatus()==BranchAssignmentStatus.ACTIVE).forEach(m->{
            m.setStatus(BranchAssignmentStatus.TRANSFERRED);
            m.setAssignedTo(r.transferDate().minusDays(1));
            mappingRepo.save(m);
        });
        Role role=s.getUser().getRole();
        StaffBranchMapping newMap=StaffBranchMapping.builder()
                .staff(s).branchId(r.newBranchId()).role(role).department(s.getDepartment()).shift(s.getShift())
                .assignedFrom(r.transferDate()).status(BranchAssignmentStatus.ACTIVE).createdBy(actor).build();
        newMap=mappingRepo.save(newMap);
        audit.log(actor,s.getUser().getUserId(),"TRANSFER_STAFF_BRANCH","Transferred to "+r.newBranchId());
        return mappingMapper.toResponse(newMap);
    }

    @Override
    @Transactional(readOnly=true)
    public PageResponse<CustomerResponse> listCustomers(int page,int size){
        Page<Customer> p=customerRepo.findAll(PageRequest.of(Math.max(0,page),Math.min(100,Math.max(1,size)),Sort.by("customerId").descending()));
        return new PageResponse<>(p.getContent().stream().map(customerMapper::toResponse).toList(),
                p.getNumber(),p.getSize(),p.getTotalElements(),p.getTotalPages());
    }

    @Override
    @Transactional
    public CustomerResponse updateCustomerStatus(Long id,UpdateCustomerStatusRequest r,Long actor){
        Customer c=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException("Customer not found: "+id));
        c.setStatus(r.status());
        c.getUser().setStatus(switch(r.status()){
            case ACTIVE -> UserStatus.ACTIVE;
            case INACTIVE, BLOCKED -> UserStatus.INACTIVE;
        });
        customerRepo.save(c);users.save(c.getUser());
        audit.log(actor,c.getUser().getUserId(),"UPDATE_CUSTOMER_STATUS",r.status().name());
        return customerMapper.toResponse(c);
    }

    private Staff findStaff(Long id){return staffRepo.findById(id).orElseThrow(()->new StaffNotFoundException("Staff not found: "+id));}
}
