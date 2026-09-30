package com.agacostays.support.service.impl;

import com.agacostays.support.client.BookingServiceClient;
import com.agacostays.support.dto.request.FeedbackRequest;
import com.agacostays.support.dto.response.BookingResponse;
import com.agacostays.support.dto.response.FeedbackResponse;
import com.agacostays.support.entity.Feedback;
import com.agacostays.support.exception.AccessDeniedException;
import com.agacostays.support.exception.BusinessRuleException;
import com.agacostays.support.exception.DuplicateResourceException;
import com.agacostays.support.exception.ResourceNotFoundException;
import com.agacostays.support.mapper.FeedbackMapper;
import com.agacostays.support.producer.SupportFeedbackEventProducer;
import com.agacostays.support.repository.FeedbackRepository;
import com.agacostays.support.security.CurrentUserProvider;
import com.agacostays.support.service.FeedbackService;
import com.agacostays.support.validation.SupportFeedbackValidationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repo;
    private final BookingServiceClient bookingClient;
    private final FeedbackMapper mapper;
    private final CurrentUserProvider currentUser;
    private final SupportFeedbackEventProducer producer;
    private final SupportFeedbackValidationService validation;

    public FeedbackServiceImpl(
            FeedbackRepository repo,
            BookingServiceClient bookingClient,
            FeedbackMapper mapper,
            CurrentUserProvider currentUser,
            SupportFeedbackEventProducer producer,
            SupportFeedbackValidationService validation) {

        this.repo = repo;
        this.bookingClient = bookingClient;
        this.mapper = mapper;
        this.currentUser = currentUser;
        this.producer = producer;
        this.validation = validation;
    }

    @Override
    @Transactional
    public FeedbackResponse create(FeedbackRequest req) {

        Long customerId = currentUser.userId();

        BookingResponse booking;

        try {
            booking = bookingClient.getBooking(
                    req.getBookingId()
            );
        } catch (Exception e) {
            throw new BusinessRuleException(
                    "Booking could not be validated"
            );
        }

        if (!customerId.equals(booking.customerId())) {
            throw new AccessDeniedException(
                    "Booking does not belong to current customer"
            );
        }

        if (!"CHECKED_OUT".equalsIgnoreCase(
                booking.status())) {

            throw new BusinessRuleException(
                    "Hotel feedback requires a completed stay"
            );
        }

        if (repo.findByBookingIdAndCustomerId(
                req.getBookingId(),
                customerId
        ).isPresent()) {

            throw new DuplicateResourceException(
                    "Feedback already exists for this booking"
            );
        }

        validation.rating(
                req.getHotelRating(),
                "hotelRating"
        );

        validation.rating(
                req.getRestaurantRating(),
                "restaurantRating"
        );

        Feedback feedback = Feedback.builder()
                .branchId(booking.branchId())
                .bookingId(req.getBookingId())
                .customerId(customerId)
                .hotelRating(req.getHotelRating())
                .hotelComments(req.getHotelComments())
                .restaurantRating(req.getRestaurantRating())
                .restaurantComments(req.getRestaurantComments())
                .feedbackType(req.getFeedbackType())
                .build();

        Feedback saved = repo.save(feedback);

        producer.feedbackSubmitted(saved);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> myFeedback() {

        return repo.findByCustomerIdOrderByCreatedAtDesc(
                        currentUser.userId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponse> byBranch(Long branchId) {

        return repo.findByBranchIdOrderByCreatedAtDesc(branchId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public FeedbackResponse update(
            Long feedbackId,
            FeedbackRequest req) {

        Feedback feedback = repo.findById(feedbackId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Feedback not found"
                        ));

        if (!feedback.getCustomerId().equals(
                currentUser.userId())) {

            throw new AccessDeniedException(
                    "You do not own this feedback"
            );
        }

        validation.rating(
                req.getHotelRating(),
                "hotelRating"
        );

        validation.rating(
                req.getRestaurantRating(),
                "restaurantRating"
        );

        if (req.getHotelRating() != null) {
            feedback.setHotelRating(
                    req.getHotelRating()
            );
        }

        if (req.getHotelComments() != null) {
            feedback.setHotelComments(
                    req.getHotelComments()
            );
        }

        if (req.getRestaurantRating() != null) {
            feedback.setRestaurantRating(
                    req.getRestaurantRating()
            );
        }

        if (req.getRestaurantComments() != null) {
            feedback.setRestaurantComments(
                    req.getRestaurantComments()
            );
        }

        if (req.getFeedbackType() != null) {
            feedback.setFeedbackType(
                    req.getFeedbackType()
            );
        }

        return mapper.toResponse(
                repo.save(feedback)
        );
    }

    @Override
    @Transactional
    public void delete(Long feedbackId) {

        Feedback feedback = repo.findById(feedbackId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Feedback not found"
                        ));

        String role = currentUser.role();

        if (!feedback.getCustomerId().equals(
                currentUser.userId())
                && !"ROOT_ADMIN".equals(role)) {

            throw new AccessDeniedException(
                    "You cannot remove this feedback"
            );
        }

        repo.delete(feedback);
    }
}