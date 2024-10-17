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
        assertEquals(698, balance);
    }

    @Test
    void testGetBalanceOfNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletService.getBalance(0));
    }

    @Test
    void testDepositsToNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletService.deposits(0, 100));
    }

    @Test
    void testWithdrawalsFromNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletService.withdrawals(0, 100));
    }

    @Test
    void testDeposits0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.deposits(1, 0));
    }

    @Test
    void testWithdrawals0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdrawals(1, 0));
    }

    @Test
    void testWithdrawalsNegativeAmountFromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> walletService.withdrawals(1, -100));
    }

    @Test
    void testWithdrawalsMoreThanGetBalanceFromUserService() {
        assertThrows(InsufficientBalanceException.class, () -> walletService.withdrawals(1, 500));
    }
}