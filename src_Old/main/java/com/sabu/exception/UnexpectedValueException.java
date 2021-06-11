package com.sabu.exception;

public class UnexpectedValueException extends ErrorException {
    public UnexpectedValueException(String message) {
        super(message, 400);
    }
}
