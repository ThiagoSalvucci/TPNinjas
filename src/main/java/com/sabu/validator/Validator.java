package com.sabu.validator;

import com.sabu.exception.*;


public class Validator {


    public static void isNotNull(Object object, String message, int statusCode) {
        if (object == null) {
            throw new NullException(message, statusCode);
        }
    }

    public static void isValidRange(int number, int min, int max, String message) {
        if (number < min || number > max) {
            throw new InvalidNumberException(message);
        }
    }

    public static void isHigherThan(int number, int min, String message) {
        if (number > min) {
            throw new InvalidNumberException(message);
        }
    }

    public static void isLowerThan(int number, int max, String message) {
        if (number < max) {
            throw new InvalidNumberException(message);
        }
    }

    public static void isExpectedValue(char value, char expectedValue, String message) {
        if (value != expectedValue) {
            throw new UnexpectedValueException(message);
        }
    }

    public static void isExpectedValue(int value, int expectedValue, String message) {
        if (value != expectedValue) {
            throw new UnexpectedValueException(message);
        }
    }

    public static void isExistingValue(Object value, Object existingValue, String message) {
        if (value.equals(existingValue)) {
            throw new ExistingValueException(message);
        }
    }

    public static void isNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new EmptyValueException(message);
        }
    }

    public static void isTrue(boolean condition, String message){
        if(!condition){
            throw new UnexpectedValueException(message);
        }
    }


}
