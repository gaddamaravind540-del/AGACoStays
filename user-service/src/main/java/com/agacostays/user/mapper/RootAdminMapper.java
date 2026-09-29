package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.RootAdminProfileResponse;
import com.agacostays.user.entity.RootAdminProfile;
import org.springframework.stereotype.Component;
@Component
public class RootAdminMapper {
    public RootAdminProfileResponse toResponse(RootAdminProfile p){
        return new RootAdminProfileResponse(p.getRootAdminId(),p.getUser().getUserId(),p.getFullName(),p.getEmail(),p.getPhone(),p.getStatus());
    }
}
