package org.example.wallet.Exceptions;

public class CannotCreateUserException extends RuntimeException {
    public CannotCreateUserException(String message) {
        super(message);
    }
}
