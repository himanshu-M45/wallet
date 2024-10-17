package org.example.wallet.Exceptions;

public class UserNotAutorisedException extends RuntimeException {
    public UserNotAutorisedException(String message) {
        super(message);
    }
}
