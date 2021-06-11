package com.sabu.exception;

public class ExistingValueException extends ErrorException {
    public ExistingValueException(String message) {
        super(message, 406);
    }
}
