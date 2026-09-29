package com.agacostays.user.exception;
public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) { super(message); }
}
