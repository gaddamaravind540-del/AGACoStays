package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.RoleResponse;
import com.agacostays.user.entity.Role;
import org.springframework.stereotype.Component;
@Component
public class RoleMapper {
    public RoleResponse toResponse(Role r){return new RoleResponse(r.getRoleId(),r.getRoleName(),r.getDescription(),r.getStatus().name());}
}
