package com.sabu.exception;

public class InvalidPositionException extends ErrorException {
    public InvalidPositionException(String message, int statusCode) {
        super(message, statusCode);
    }
}
