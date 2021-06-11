package com.sabu.exception;

public class EmptyValueException extends ErrorException {
    public EmptyValueException(String message) {
        super(message, 400);
    }
}
