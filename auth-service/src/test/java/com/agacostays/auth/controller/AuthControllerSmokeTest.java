package com.agacostays.auth.controller;

import com.agacostays.auth.security.CurrentUserResolver;
import com.agacostays.auth.service.AuthService;
import com.agacostays.auth.service.OtpService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class AuthControllerSmokeTest {

    @Test
    void controllerCanBeConstructed() {
        AuthController controller = new AuthController(
                mock(AuthService.class),
                mock(OtpService.class),
                mock(CurrentUserResolver.class)
        );

        assertThat(controller).isNotNull();
    }
}
