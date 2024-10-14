package org.example.wallet.Service;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    @Test
    void testGetBalanceOfStoredUser() {
        double balance = walletService.getBalance(1);
        assertEquals(600, balance);
    }

    @Test
    void testGetBalanceOfNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.getBalance(0));
    }

    @Test
    void testDepositToNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.deposit(0, 100));
    }

    @Test
    void testWithdrawalFromNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> walletService.withdraw(0, 100));
    }

    @Test
    void testGetBalance() {
        assertEquals(200, walletService.getBalance(3));
        double balance = walletService.deposit(3, 100);
        assertEquals(balance, walletService.getBalance(3));
    }

    @Test
    void testDepositFromUserService() {
        assertEquals(250, walletService.getBalance(2));
        double balance = walletService.deposit(2, 100);
        assertEquals(balance, walletService.getBalance(2));
    }

    @Test
    void testDeposit0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.deposit(1, 0));
    }

    @Test
    void testWithdrawal0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdraw(1, 0));
    }

    @Test
    void testWithdrawalNegativeAmountFromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdraw(1, -100));
    }

    @Test
    void testWithdrawalMoreThanGetBalanceFromUserService() {
        assertThrows(InsufficientBalanceException.class, () -> walletService.withdraw(1, 500));
    }

    @Test
    void testWithdrawal50FromUserService() {
        assertEquals(750, walletService.getBalance(1));
        double balance = walletService.withdraw(1, 150);
        assertEquals(balance, walletService.getBalance(1));
    }
}