package com.agacostays.room.exception;

public class BranchAccessDeniedException extends RuntimeException {
    public BranchAccessDeniedException(String message) {
        super(message);
    }
}
