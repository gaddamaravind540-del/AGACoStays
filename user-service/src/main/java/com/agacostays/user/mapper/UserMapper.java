package com.agacostays.user.mapper;
import com.agacostays.user.dto.response.*;
import com.agacostays.user.entity.User;
import org.springframework.stereotype.Component;
@Component
public class UserMapper {
    public UserResponse toResponse(User u){
        return new UserResponse(u.getUserId(),u.getFullName(),u.getEmail(),u.getPhone(),
                u.getRole().getRoleName(),u.getUserType().name(),u.getStatus().name());
    }
}
