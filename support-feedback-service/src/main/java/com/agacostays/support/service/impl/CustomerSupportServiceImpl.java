package com.agacostays.support.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agacostays.support.client.BookingServiceClient;
import com.agacostays.support.dto.request.AssignSupportRequest;
import com.agacostays.support.dto.request.CreateSupportRequest;
import com.agacostays.support.dto.request.UpdateSupportStatusRequest;
import com.agacostays.support.dto.response.PageResponse;
import com.agacostays.support.dto.response.SupportRequestResponse;
import com.agacostays.support.entity.CustomerSupportRequest;
import com.agacostays.support.enums.SupportStatus;
import com.agacostays.support.exception.AccessDeniedException;
import com.agacostays.support.exception.BusinessRuleException;
import com.agacostays.support.exception.InvalidStatusException;
import com.agacostays.support.exception.ResourceNotFoundException;
import com.agacostays.support.mapper.CustomerSupportRequestMapper;
import com.agacostays.support.producer.SupportFeedbackEventProducer;
import com.agacostays.support.repository.CustomerSupportRequestRepository;
import com.agacostays.support.security.CurrentUserProvider;
import com.agacostays.support.service.CustomerSupportService;
import com.agacostays.support.validation.SupportFeedbackValidationService;

@Service
public class CustomerSupportServiceImpl implements CustomerSupportService {

    private final CustomerSupportRequestRepository repo;
    private final CustomerSupportRequestMapper mapper;
    private final CurrentUserProvider currentUser;
    private final BookingServiceClient bookingClient;
    private final SupportFeedbackEventProducer producer;
    private final SupportFeedbackValidationService validation;

    public CustomerSupportServiceImpl(CustomerSupportRequestRepository repo,
                                      CustomerSupportRequestMapper mapper,
                                      CurrentUserProvider currentUser,
                                      BookingServiceClient bookingClient,
                                      SupportFeedbackEventProducer producer,
                                      SupportFeedbackValidationService validation) {
        this.repo = repo;
        this.mapper = mapper;
        this.currentUser = currentUser;
        this.bookingClient = bookingClient;
        this.producer = producer;
        this.validation = validation;
    }

    @Override
    @Transactional
    public SupportRequestResponse create(CreateSupportRequest request) {
        Long customerId = currentUser.userId();

        if (request.getBookingId() != null) {
            try {
                var booking = bookingClient.getBooking(request.getBookingId());
                if (!customerId.equals(booking.customerId())) {
                    throw new AccessDeniedException("Booking does not belong to current customer");
                }
                if (request.getBranchId() != null && !request.getBranchId().equals(booking.branchId())) {
                    throw new BusinessRuleException("Booking does not belong to requested branch");
                }
            } catch (AccessDeniedException | BusinessRuleException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new BusinessRuleException("Booking could not be validated");
            }
        }

        if (request.getBranchId() == null) {
            throw new BusinessRuleException("branchId is required for a support request");
        }

        CustomerSupportRequest entity = CustomerSupportRequest.builder()
                .branchId(request.getBranchId())
                .customerId(customerId)
                .bookingId(request.getBookingId())
                .requestType(request.getRequestType())
                .subject(request.getSubject())
                .description(request.getDescription())
                .priority(request.getPriority())
                .status(SupportStatus.OPEN)
                .build();

        CustomerSupportRequest saved = repo.save(entity);
        producer.supportCreated(saved);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportRequestResponse> myRequests() {
        return repo.findByCustomerIdOrderByCreatedAtDesc(currentUser.userId())
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SupportRequestResponse get(Long requestId) {
        CustomerSupportRequest entity = repo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Support request not found"));

        if (entity.getCustomerId().equals(currentUser.userId())
                || isStaffRole(currentUser.role())) {
            return mapper.toResponse(entity);
        }
        throw new AccessDeniedException("You are not allowed to view this support request");
    }

    @Override
    @Transactional
    public SupportRequestResponse updateStatus(Long requestId, UpdateSupportStatusRequest request) {
        CustomerSupportRequest entity = repo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Support request not found"));

        SupportStatus old = entity.getStatus();

        if (old == SupportStatus.CLOSED && request.getStatus() != SupportStatus.CLOSED) {
            throw new InvalidStatusException("Closed support request cannot be reopened");
        }

        entity.setStatus(request.getStatus());
        CustomerSupportRequest saved = repo.save(entity);
        producer.supportStatusChanged(saved, old, request.getRemarks());
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public SupportRequestResponse assign(Long requestId, AssignSupportRequest request) {
        CustomerSupportRequest entity = repo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Support request not found"));

        entity.setAssignedTo(request.getAssignedTo());
        if (entity.getStatus() == SupportStatus.OPEN) {
            entity.setStatus(SupportStatus.IN_PROGRESS);
        }

        CustomerSupportRequest saved = repo.save(entity);
        producer.supportStatusChanged(saved, entity.getStatus(), "Assigned to " + request.getAssignedTo());
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<SupportRequestResponse> search(Long branchId, Pageable pageable) {
        Page<CustomerSupportRequest> page = repo.findByBranchId(branchId, pageable);

        return PageResponse.<SupportRequestResponse>builder()
                .content(page.getContent().stream().map(mapper::toResponse).toList())
                .page(page.getNumber()).size(page.getSize())
                .totalElements(page.getTotalElements()).totalPages(page.getTotalPages()).build();
    }

    private boolean isStaffRole(String role) {
        return switch (role) {
            case "ROOT_ADMIN","MANAGER","RECEPTIONIST","RESTAURANT_ADMIN","CHEF","SERVING_STAFF","HOUSEKEEPING_STAFF" -> true;
            default -> false;
        };
    }
}
