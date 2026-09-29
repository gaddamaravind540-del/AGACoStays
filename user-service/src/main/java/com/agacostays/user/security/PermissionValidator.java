package com.agacostays.user.security;
import com.agacostays.user.entity.RolePermission;
import com.agacostays.user.exception.PermissionDeniedException;
import com.agacostays.user.repository.RolePermissionRepository;
import com.agacostays.user.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator {
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    public PermissionValidator(RoleRepository rr,RolePermissionRepository rpr){this.roleRepository=rr;this.rolePermissionRepository=rpr;}
    public void requirePermission(String roleName,String permissionCode){
        var role=roleRepository.findByRoleNameIgnoreCase(roleName)
                .orElseThrow(()->new PermissionDeniedException("Role not found"));
        boolean allowed=rolePermissionRepository.findByRole_RoleId(role.getRoleId()).stream()
                .map(RolePermission::getPermission).anyMatch(p->p.getPermissionCode().equalsIgnoreCase(permissionCode));
        if(!allowed && !"ROOT_ADMIN".equalsIgnoreCase(roleName)){
            throw new PermissionDeniedException("Missing permission: "+permissionCode);
        }
    }
}
