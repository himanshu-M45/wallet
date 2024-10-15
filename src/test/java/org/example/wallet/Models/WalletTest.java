package org.example.wallet.Models;

import org.example.wallet.Enums.TransactionType;
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
    void testWithdrawal() {
        Wallet wallet = new Wallet();
        wallet.deposit(100.0);
        wallet.withdrawal(50.0);
        assertEquals(50.0, wallet.getBalance());
    }

    @Test
    void testWithdrawalNegativeAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.withdrawal(-100.0));
    }

    @Test
    void testWithdrawalZeroAmount() {
        Wallet wallet = new Wallet();
        assertThrows(InvalidAmountEnteredException.class, () -> wallet.withdrawal(0.0));
    }

    @Test
    void testWithdrawalInsufficientGetBalance() {
        Wallet wallet = new Wallet();
        wallet.deposit(50.0);
        assertEquals(50.0, wallet.getBalance());
        assertThrows(InsufficientBalanceException.class, () -> wallet.withdrawal(100.0));
    }

    @Test
    void testGetTransactions() {
        Wallet wallet = new Wallet();

        wallet.deposit(100.0);
        wallet.addTransaction("100.0 added to balance", TransactionType.DEPOSIT);
        wallet.withdrawal(50.0);
        wallet.addTransaction("50.0 deducted from balance", TransactionType.WITHDRAWAL);

        assertEquals(2, wallet.getTransactions().size());
    }
}