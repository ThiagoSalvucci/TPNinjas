package com.sabu.exception;

public class ErrorException extends RuntimeException {
    private final int statusCode;

    public ErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public Error getAsError() {
        return new Error(getMessage(), statusCode);
    }


}
