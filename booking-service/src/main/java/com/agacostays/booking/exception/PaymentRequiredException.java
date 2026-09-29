package com.agacostays.booking.exception;

public class PaymentRequiredException extends RuntimeException {
    public PaymentRequiredException() { super("Payment is required"); }
    public PaymentRequiredException(String message) { super(message); }
}
