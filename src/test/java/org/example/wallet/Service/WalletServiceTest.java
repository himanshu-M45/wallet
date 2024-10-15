package org.example.wallet.Service;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Exceptions.WalletNotFoundException;
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
        assertThrows(WalletNotFoundException.class, () -> walletService.getBalance(0));
    }

    @Test
    void testDepositToNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletService.deposit(0, 100));
    }

    @Test
    void testWithdrawalFromNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletService.withdrawal(0, 100));
    }

    @Test
    void testDepositFromUserService() {
        assertEquals(450, walletService.getBalance(1));
        double balance = walletService.deposit(1, 150);
        assertEquals(balance, walletService.getBalance(1));
    }

    @Test
    void testDeposit0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.deposit(1, 0));
    }

    @Test
    void testWithdrawal0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdrawal(1, 0));
    }

    @Test
    void testWithdrawalNegativeAmountFromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdrawal(1, -100));
    }

    @Test
    void testWithdrawalMoreThanGetBalanceFromUserService() {
        assertThrows(InsufficientBalanceException.class, () -> walletService.withdrawal(1, 500));
    }

    @Test
    void testWithdrawal50FromUserService() {
        assertEquals(600, walletService.getBalance(1));
        double balance = walletService.withdrawal(1, 150);
        assertEquals(balance, walletService.getBalance(1));
    }
}