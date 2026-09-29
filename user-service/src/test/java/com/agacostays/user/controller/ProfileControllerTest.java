package com.agacostays.user.controller;

import com.agacostays.user.security.CurrentUserProvider;
import com.agacostays.user.service.ProfileService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ProfileControllerTest {
 @Test void controllerConstructs(){assertThat(new ProfileController(mock(ProfileService.class),mock(CurrentUserProvider.class))).isNotNull();}
}
