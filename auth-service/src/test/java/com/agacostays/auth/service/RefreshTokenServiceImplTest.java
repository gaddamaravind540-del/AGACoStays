package com.agacostays.auth.service;

import com.agacostays.auth.entity.Role;
import com.agacostays.auth.entity.User;
import com.agacostays.auth.enums.AuthProvider;
import com.agacostays.auth.enums.UserStatus;
import com.agacostays.auth.enums.UserType;
import com.agacostays.auth.repository.RefreshTokenRepository;
import com.agacostays.auth.service.impl.RefreshTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RefreshTokenServiceImplTest {

    @Test
    void createStoresTokenHash() {
        RefreshTokenRepository repository = mock(RefreshTokenRepository.class);
        RefreshTokenService service = new RefreshTokenServiceImpl(repository, 7);

        User user = User.builder()
                .userId(1L)
                .fullName("Customer")
                .email("customer@example.com")
                .role(Role.builder().roleName("CUSTOMER").build())
                .userType(UserType.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .authProvider(AuthProvider.LOCAL)
                .build();

        String token = service.create(user);

        assertThat(token).isNotBlank();
        verify(repository).save(any());
    }
}
