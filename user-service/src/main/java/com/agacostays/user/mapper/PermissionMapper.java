package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.PermissionResponse;
import com.agacostays.user.entity.Permission;
import org.springframework.stereotype.Component;
@Component
public class PermissionMapper {
    public PermissionResponse toResponse(Permission p){return new PermissionResponse(p.getPermissionId(),p.getPermissionCode(),p.getPermissionName(),p.getDescription(),p.getModuleName());}
}
