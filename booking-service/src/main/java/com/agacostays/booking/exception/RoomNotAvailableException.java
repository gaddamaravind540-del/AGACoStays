package com.agacostays.booking.exception;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException() { super("Room is not available"); }
    public RoomNotAvailableException(String message) { super(message); }
}
