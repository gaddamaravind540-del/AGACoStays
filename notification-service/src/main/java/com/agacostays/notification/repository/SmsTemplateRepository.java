package com.agacostays.notification.repository;

import com.agacostays.notification.entity.SmsTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsTemplateRepository extends JpaRepository<SmsTemplate, Long> {
    Optional<SmsTemplate> findByTemplateCodeAndActiveTrue(String templateCode);
    boolean existsByTemplateCode(String templateCode);
}
