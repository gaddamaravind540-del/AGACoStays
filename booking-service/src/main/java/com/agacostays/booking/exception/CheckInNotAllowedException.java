package com.agacostays.booking.exception;

public class CheckInNotAllowedException extends RuntimeException {
    public CheckInNotAllowedException() { super("Check-in is not allowed"); }
    public CheckInNotAllowedException(String message) { super(message); }
}
