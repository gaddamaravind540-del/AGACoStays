package com.agacostays.user.service;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*; import java.util.List; public interface RoleService {
 RoleResponse createRole(CreateRoleRequest r,Long actor); List<RoleResponse> listRoles(); RoleResponse updateRole(Long id,UpdateRoleRequest r,Long actor); void deleteRole(Long id,Long actor);
 RolePermissionResponse assignPermission(Long roleId,AssignPermissionRequest r,Long actor); List<RolePermissionResponse> permissions(Long roleId);
}