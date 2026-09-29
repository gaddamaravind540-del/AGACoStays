package com.agacostays.notification.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record BookingEmailRequest(
        Long branchId, Long customerId, Long bookingId,
        @NotBlank String customerName,
        @NotBlank @Email String email,
        @NotBlank String hotelBranchName,
        @NotBlank String city,
        String hotelAddress,
        String mapLink,
        String receptionistContact,
        String emergencyContact,
        String roomNumber,
        String roomType,
        List<String> roomPhotos,
        String roomDescription,
        java.math.BigDecimal pricePerDay,
        LocalDateTime checkIn,
        LocalDateTime checkout,
        String paymentStatus,
        String paymentLink,
        java.math.BigDecimal totalAmount
) {}
