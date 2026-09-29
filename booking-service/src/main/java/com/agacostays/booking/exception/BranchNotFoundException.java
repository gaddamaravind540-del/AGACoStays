package com.agacostays.booking.exception;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException() { super("Branch not found"); }
    public BranchNotFoundException(String message) { super(message); }
}
