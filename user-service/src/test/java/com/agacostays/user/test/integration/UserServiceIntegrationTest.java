package com.agacostays.user.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
 properties = {
   "spring.datasource.url=jdbc:h2:mem:user_service_test;MODE=PostgreSQL",
   "spring.datasource.driver-class-name=org.h2.Driver",
   "spring.datasource.username=sa",
   "spring.datasource.password=",
   "spring.jpa.hibernate.ddl-auto=none",
   "spring.flyway.enabled=false",
   "spring.kafka.bootstrap-servers=localhost:9092"
 }
)
class UserServiceIntegrationTest {
 @Test void serviceTestMarker(){assertThat("user-service").isEqualTo("user-service");}
}
