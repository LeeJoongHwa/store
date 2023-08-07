package com.example.store.signup.exception;

public class ExistUserException extends RuntimeException {
    public ExistUserException(String message) {
        super(message);
    }
}
