package org.example.wallet.Models;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    @Test
    void testValidWalletInitialization() {
        Wallet wallet = new Wallet();
        assertEquals(0.0, wallet.getBalance());
    }

    @Test
    void testDeposit() {
        Wallet wallet = new Wallet();
        wallet.deposit(100.0);
        assertEquals(100.0, wallet.getBalance());
    }

    @Test
    void testDepositNegativeAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.deposit(-100.0));
    }

    @Test
    void testDepositZeroAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.deposit(0.0));
    }

    @Test
    void testWithdraw() {
        Wallet wallet = new Wallet();
        wallet.deposit(100.0);
        wallet.withdraw(50.0);
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void testWithdrawNegativeAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.withdraw(-100.0));
    }

    @Test
    void testWithdrawZeroAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.withdraw(0.0));
    }

    @Test
    void testWithdrawInsufficientBalance() {
        Wallet wallet = new Wallet();
        wallet.deposit(50.0);
        assertEquals(50.0, wallet.getBalance());
        assertThrows(InsufficientBalanceException.class, () -> wallet.withdraw(100.0));
    }
}