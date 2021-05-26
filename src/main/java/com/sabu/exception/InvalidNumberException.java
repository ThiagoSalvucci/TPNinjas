package com.sabu.exception;

public class InvalidNumberException extends ErrorException {
    public InvalidNumberException(String message) {
        super(message, 400);
    }
}
