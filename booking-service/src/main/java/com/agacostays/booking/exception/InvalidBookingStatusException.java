package com.agacostays.booking.exception;

public class InvalidBookingStatusException extends RuntimeException {
    public InvalidBookingStatusException() { super("Invalid booking status transition"); }
    public InvalidBookingStatusException(String message) { super(message); }
}
