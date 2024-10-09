package org.example.wallet.Exceptions;

public class CannotOwnWalletWithoutProperUser extends RuntimeException {
    public CannotOwnWalletWithoutProperUser(String message) {
        super(message);
    }
}
