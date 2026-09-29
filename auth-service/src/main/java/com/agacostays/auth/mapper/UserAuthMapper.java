package com.agacostays.auth.mapper;

import com.agacostays.auth.dto.response.UserAuthResponse;
import com.agacostays.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper {

    public UserAuthResponse toResponse(User user) {
        return new UserAuthResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().getRoleName(),
                user.getUserType().name(),
                user.getStatus().name()
        );
    }
}
