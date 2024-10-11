package org.example.wallet.Service;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    @Test
    void testGetBalanceOfNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.getBalance(0));
    }

    @Test
    void testDepositToNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.deposit(0, 100));
    }

    @Test
    void testWithdrawFromNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.withdraw(0, 100));
    }

    @Test
    void testGetBalanceOfStoredUserAndUpdateBalance() {
        assertEquals(100, walletService.getBalance(3));
        double balance = walletService.deposit(3, 100);
        assertEquals(balance, walletService.getBalance(3));
    }

    @Test
    void testDepositFromWalletService() {
        assertEquals(200, walletService.getBalance(2));
        double balance = walletService.deposit(2, 100);
        assertEquals(balance, walletService.getBalance(2));
    }

    @Test
    void testDeposit0FromWalletService() {
        assertThrows(IllegalArgumentException.class, () -> walletService.deposit(1, 0));
    }

    @Test
    void testWithdraw0FromWalletService() {
        assertThrows(IllegalArgumentException.class, () -> walletService.withdraw(1, 0));
    }

    @Test
    void testWithdrawNegativeAmountFromWalletService() {
        assertThrows(IllegalArgumentException.class, () -> walletService.withdraw(1, -100));
    }

    @Test
    void testWithdrawMoreThanBalanceFromWalletService() {
        assertThrows(InsufficientBalanceException.class, () -> walletService.withdraw(1, 500));
    }

    @Test
    void testWithdraw50FromWalletService() {
        assertEquals(300, walletService.getBalance(1));
        double balance = walletService.withdraw(1, 50);
        assertEquals(balance, walletService.getBalance(1));
    }
}