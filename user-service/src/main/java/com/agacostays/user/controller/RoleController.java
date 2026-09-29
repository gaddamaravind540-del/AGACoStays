package com.agacostays.user.controller;
import com.agacostays.user.dto.request.*; import com.agacostays.user.dto.response.*; import com.agacostays.user.security.CurrentUserProvider;
import com.agacostays.user.service.RoleService; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api")
public class RoleController {
 private final RoleService s; private final CurrentUserProvider c;
 public RoleController(RoleService s,CurrentUserProvider c){this.s=s;this.c=c;}
 @PreAuthorize("hasRole('ROOT_ADMIN')")
 @PostMapping("/roles") public ApiResponse<RoleResponse> create(@Valid @RequestBody CreateRoleRequest r){return ApiResponse.success("Role created",s.createRole(r,c.userId()),"unknown");}
 @PreAuthorize("hasAnyRole('ROOT_ADMIN','MANAGER')")
 @GetMapping("/roles") public ApiResponse<List<RoleResponse>> list(){return ApiResponse.success("Roles",s.listRoles(),"unknown");}
 @PreAuthorize("hasRole('ROOT_ADMIN')")
 @PutMapping("/roles/{id}") public ApiResponse<RoleResponse> update(@PathVariable Long id,@Valid @RequestBody UpdateRoleRequest r){return ApiResponse.success("Role updated",s.updateRole(id,r,c.userId()),"unknown");}
 @PreAuthorize("hasRole('ROOT_ADMIN')")
 @DeleteMapping("/roles/{id}") public ApiResponse<Void> delete(@PathVariable Long id){s.deleteRole(id,c.userId());return ApiResponse.success("Role deactivated",null,"unknown");}
 @PreAuthorize("hasRole('ROOT_ADMIN')")
 @PostMapping("/roles/{id}/permissions") public ApiResponse<RolePermissionResponse> assign(@PathVariable Long id,@Valid @RequestBody AssignPermissionRequest r){return ApiResponse.success("Permission assigned",s.assignPermission(id,r,c.userId()),"unknown");}
 @PreAuthorize("hasAnyRole('ROOT_ADMIN','MANAGER')")
 @GetMapping("/roles/{id}/permissions") public ApiResponse<List<RolePermissionResponse>> permissions(@PathVariable Long id){return ApiResponse.success("Role permissions",s.permissions(id),"unknown");}
}
