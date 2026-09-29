package com.agacostays.booking.exception;

public class CustomerMismatchException extends RuntimeException {
    public CustomerMismatchException() { super("Customer does not own this booking"); }
    public CustomerMismatchException(String message) { super(message); }
}
