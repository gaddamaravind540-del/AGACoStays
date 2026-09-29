package com.agacostays.user.service.impl;
import com.agacostays.user.dto.request.CreatePermissionRequest; import com.agacostays.user.dto.response.PermissionResponse;
import com.agacostays.user.entity.Permission; import com.agacostays.user.exception.DuplicatePermissionException;
import com.agacostays.user.mapper.PermissionMapper; import com.agacostays.user.repository.PermissionRepository;
import com.agacostays.user.service.PermissionService; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
 private final PermissionRepository repo; private final PermissionMapper mapper; private final UserAuditService audit;
 public PermissionServiceImpl(PermissionRepository r,PermissionMapper m,UserAuditService a){repo=r;mapper=m;audit=a;}
 @Transactional public PermissionResponse create(CreatePermissionRequest r,Long actor){
  if(repo.findByPermissionCodeIgnoreCase(r.permissionCode()).isPresent()) throw new DuplicatePermissionException("Permission code already exists");
  Permission p=repo.save(Permission.builder().permissionCode(r.permissionCode().trim().toUpperCase()).permissionName(r.permissionName())
      .description(r.description()).moduleName(r.moduleName().trim().toUpperCase()).build());
  audit.log(actor,null,"CREATE_PERMISSION",p.getPermissionCode());return mapper.toResponse(p);
 }
 @Transactional(readOnly=true) public List<PermissionResponse> list(){return repo.findAll().stream().map(mapper::toResponse).toList();}
}
