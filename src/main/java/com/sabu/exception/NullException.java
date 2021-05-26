package com.sabu.exception;

public class NullException extends ErrorException {
    public NullException(String message, int statusCode) {
        super(message, statusCode);
    }
}
