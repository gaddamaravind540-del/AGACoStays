package com.agacostays.user.exception;
public class FeignClientException extends RuntimeException {
    public FeignClientException(String message) { super(message); }
}
