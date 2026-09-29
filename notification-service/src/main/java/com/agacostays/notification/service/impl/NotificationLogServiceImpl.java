package com.agacostays.notification.service.impl;

import com.agacostays.notification.dto.response.NotificationResponse;
import com.agacostays.notification.mapper.NotificationLogMapper;
import com.agacostays.notification.repository.NotificationLogRepository;
import com.agacostays.notification.service.NotificationLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NotificationLogServiceImpl implements NotificationLogService {
    private final NotificationLogRepository repo; private final NotificationLogMapper mapper;
    public NotificationLogServiceImpl(NotificationLogRepository repo,NotificationLogMapper mapper){this.repo=repo;this.mapper=mapper;}
    @Override public Page<NotificationResponse> search(Long branchId,Pageable pageable){return (branchId==null?repo.findAll(pageable):repo.findByBranchIdOrderByCreatedAtDesc(branchId,pageable)).map(mapper::toResponse);}
}
