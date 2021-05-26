package com.sabu.exception;

public class Error {
    private final int statusCode;
    private final String message;

    public Error(String message, int statusCode) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
