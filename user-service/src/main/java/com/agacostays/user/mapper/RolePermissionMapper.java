package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.RolePermissionResponse;
import com.agacostays.user.entity.RolePermission;
import org.springframework.stereotype.Component;
@Component
public class RolePermissionMapper {
    public RolePermissionResponse toResponse(RolePermission rp){
        return new RolePermissionResponse(rp.getRolePermissionId(),rp.getRole().getRoleId(),rp.getPermission().getPermissionId(),rp.getPermission().getPermissionCode(),rp.getPermission().getPermissionName());
    }
}
