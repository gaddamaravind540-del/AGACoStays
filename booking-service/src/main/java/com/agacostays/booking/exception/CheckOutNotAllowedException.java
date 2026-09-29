package com.agacostays.booking.exception;

public class CheckOutNotAllowedException extends RuntimeException {
    public CheckOutNotAllowedException() { super("Check-out is not allowed"); }
    public CheckOutNotAllowedException(String message) { super(message); }
}
