package com.agacostays.room.exception;

public class ActiveBookingFoundException extends RuntimeException {
    public ActiveBookingFoundException(String message) {
        super(message);
    }
}
