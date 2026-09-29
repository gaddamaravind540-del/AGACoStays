package com.agacostays.attendance.service.impl;

import com.agacostays.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceValidationServiceImpl extends com.agacostays.attendance.validation.AttendanceValidationServiceImpl implements com.agacostays.attendance.service.AttendanceValidationService {
    public AttendanceValidationServiceImpl(AttendanceRepository repo) { super(repo); }
}
