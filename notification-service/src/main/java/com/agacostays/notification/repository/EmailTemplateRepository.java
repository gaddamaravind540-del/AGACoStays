package com.agacostays.notification.repository;

import com.agacostays.notification.entity.EmailTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByTemplateCodeAndActiveTrue(String templateCode);
    boolean existsByTemplateCode(String templateCode);
}
