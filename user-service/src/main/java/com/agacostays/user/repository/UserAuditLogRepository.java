package com.agacostays.user.repository;
import com.agacostays.user.audit.UserAuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserAuditLogRepository extends JpaRepository<UserAuditLog, Long> {}
