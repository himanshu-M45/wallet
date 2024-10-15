package org.example.wallet.Service;

import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WalletQueryServiceTest {
    @Autowired
    private WalletQueryService walletQueryService;

    @Test
    void testGetBalanceOfStoredUser() {
        double balance = walletQueryService.getBalance(1);
        assertEquals(600, balance);
    }

    @Test
    void testGetBalanceOfNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletQueryService.getBalance(0));
    }

    @Test
    void testGetTransactionsOfStoredUser() {
        List<Transaction> transactions = walletQueryService.getTransactions(1);
        assertNotNull(transactions);
    }

    @Test
    void testGetTransactionsOfNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> walletQueryService.getTransactions(0));
    }
}