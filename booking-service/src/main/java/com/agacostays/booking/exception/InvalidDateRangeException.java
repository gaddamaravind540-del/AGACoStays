package com.agacostays.booking.exception;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException() { super("Invalid date range"); }
    public InvalidDateRangeException(String message) { super(message); }
}
