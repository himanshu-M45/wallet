package org.example.wallet.Exceptions;

public class NoTransactionFoundException extends RuntimeException {
    public NoTransactionFoundException(String message) {
        super(message);
    }
}
