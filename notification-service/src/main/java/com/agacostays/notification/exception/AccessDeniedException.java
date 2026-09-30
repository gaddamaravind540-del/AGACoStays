package com.agacostays.notification.exception;

@SuppressWarnings("serial")
public class AccessDeniedException extends RuntimeException {
	public AccessDeniedException(String message) {
		super(message);
	}
}
