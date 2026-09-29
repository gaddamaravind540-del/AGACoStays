package com.agacostays.booking.exception;

public class BookingNotAllowedException extends RuntimeException {
    public BookingNotAllowedException() { super("Booking action is not allowed"); }
    public BookingNotAllowedException(String message) { super(message); }
}
