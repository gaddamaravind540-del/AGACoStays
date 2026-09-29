package com.agacostays.branch.controller;
import com.agacostays.branch.security.CurrentUserProvider; import com.agacostays.branch.service.CityService; import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat; import static org.mockito.Mockito.mock;
class CityControllerTest{@Test void constructs(){assertThat(new CityController(mock(CityService.class),mock(CurrentUserProvider.class))).isNotNull();}}
