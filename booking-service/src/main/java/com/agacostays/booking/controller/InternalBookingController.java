package com.agacostays.booking.controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/internal/api/bookings")
public class InternalBookingController {

    private final JdbcTemplate jdbcTemplate;

    public InternalBookingController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public record BookingStatusResponse(Long roomId, boolean bookingExists, String message) {}

    @GetMapping("/room-availability")
    public BookingStatusResponse checkRoomAvailability(
            @RequestParam Long roomId,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate) {

        try {
            // Check for overlapping active bookings in booking_db
            String sql = "SELECT COUNT(*) FROM bookings " +
                         "WHERE room_id = ? " +
                         "AND status IN ('CONFIRMED', 'CHECKED_IN', 'BOOKED') " +
                         "AND NOT (check_out_date <= ? OR check_in_date >= ?)";

            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, roomId, checkInDate, checkOutDate);
            boolean exists = count != null && count > 0;

            return new BookingStatusResponse(
                    roomId,
                    exists,
                    exists ? "Active reservation overlaps the selected dates" : "Room is available"
            );
        } catch (Exception ex) {
            // If bookings table is empty or not yet generated, assume no overlapping booking exists
            return new BookingStatusResponse(roomId, false, "Available");
        }
    }
}