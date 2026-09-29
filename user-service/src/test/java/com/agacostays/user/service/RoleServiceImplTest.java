package com.agacostays.user.service;

import com.agacostays.user.mapper.RoleMapper;
import com.agacostays.user.mapper.RolePermissionMapper;
import com.agacostays.user.repository.PermissionRepository;
import com.agacostays.user.repository.RolePermissionRepository;
import com.agacostays.user.repository.RoleRepository;
import com.agacostays.user.service.impl.RoleServiceImpl;
import com.agacostays.user.service.impl.UserAuditService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class RoleServiceImplTest {
 @Test void canConstructService(){
   RoleServiceImpl service=new RoleServiceImpl(
     mock(RoleRepository.class),mock(PermissionRepository.class),mock(RolePermissionRepository.class),
     mock(RoleMapper.class),mock(RolePermissionMapper.class),mock(UserAuditService.class)
   );
   assertThat(service).isNotNull();
 }
}
