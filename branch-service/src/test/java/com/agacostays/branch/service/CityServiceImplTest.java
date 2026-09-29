package com.agacostays.branch.service;
import com.agacostays.branch.mapper.CityMapper;
import com.agacostays.branch.repository.CityRepository;
import com.agacostays.branch.service.impl.CityServiceImpl;
import com.agacostays.branch.validation.CityValidationService;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CityServiceImplTest {
 @Test void constructs(){assertThat(new CityServiceImpl(mock(CityRepository.class),mock(CityMapper.class),mock(CityValidationService.class))).isNotNull();}
}
