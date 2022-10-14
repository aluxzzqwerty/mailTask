package com.epam.exceptions;

public class NoSuchNameInListException extends RuntimeException{
    public NoSuchNameInListException(String errorMessage) {
        super(errorMessage);
    }
}
