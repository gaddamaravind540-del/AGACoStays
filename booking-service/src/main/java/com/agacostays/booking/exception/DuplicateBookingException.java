package com.agacostays.booking.exception;

public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException() { super("Duplicate booking detected"); }
    public DuplicateBookingException(String message) { super(message); }
}
