package org.example.wallet.Exceptions;

public class UserIsNotRegisteredException extends RuntimeException {
    public UserIsNotRegisteredException(String message) {
        super(message);
    }
}
