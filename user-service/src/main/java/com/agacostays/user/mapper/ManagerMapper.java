package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.ManagerResponse;
import com.agacostays.user.entity.ManagerProfile;
import org.springframework.stereotype.Component;
@Component
public class ManagerMapper {
    public ManagerResponse toResponse(ManagerProfile p){
        return new ManagerResponse(p.getManagerId(),p.getUser().getUserId(),p.getFullName(),p.getEmail(),p.getPhone(),p.getAccessLevel().name(),p.getStatus());
    }
}
