package com.agacostays.branch.test.integration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties={
 "spring.datasource.url=jdbc:h2:mem:branch_test;MODE=PostgreSQL",
 "spring.datasource.driver-class-name=org.h2.Driver",
 "spring.datasource.username=sa",
 "spring.datasource.password=",
 "spring.jpa.hibernate.ddl-auto=none",
 "spring.flyway.enabled=false",
 "spring.kafka.bootstrap-servers=localhost:9092"
})
class BranchServiceIntegrationTest {
 @Test void serviceMarker(){assertThat("branch-service").isEqualTo("branch-service");}
}
