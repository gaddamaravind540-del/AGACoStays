package com.agacostays.user.service.impl;
import com.agacostays.user.audit.UserAuditLog;
import com.agacostays.user.repository.UserAuditLogRepository;
import org.springframework.stereotype.Service;
@Service
public class UserAuditService {
 private final UserAuditLogRepository repository;
 public UserAuditService(UserAuditLogRepository repository){this.repository=repository;}
 public void log(Long actor,Long target,String action,String desc){
  repository.save(UserAuditLog.builder().actorUserId(actor).targetUserId(target).action(action).description(desc).build());
 }
}
