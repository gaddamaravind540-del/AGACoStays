package com.agacostays.booking.exception;

public class BranchAccessDeniedException extends RuntimeException {
    public BranchAccessDeniedException() { super("Branch access denied"); }
    public BranchAccessDeniedException(String message) { super(message); }
}
