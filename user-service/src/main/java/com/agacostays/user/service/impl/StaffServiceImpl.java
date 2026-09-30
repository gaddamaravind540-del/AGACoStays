package com.agacostays.user.service.impl;

import com.agacostays.user.dto.response.StaffBranchMappingResponse;
import com.agacostays.user.mapper.StaffBranchMappingMapper;
import com.agacostays.user.repository.StaffBranchMappingRepository;
import com.agacostays.user.service.StaffService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffBranchMappingRepository repo;
    private final StaffBranchMappingMapper mapper;

    public StaffServiceImpl(
            StaffBranchMappingRepository repo,
            StaffBranchMappingMapper mapper) {

        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<StaffBranchMappingResponse> getMyBranchMappings(
            Long userId) {

        return repo.findByStaff_User_UserId(userId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}