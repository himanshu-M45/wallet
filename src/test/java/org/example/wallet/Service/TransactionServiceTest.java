package org.example.wallet.Service;

import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;

    @Test
    void testGetTransactionOfStoredUser() {
        List<Transaction> transactions = transactionService.getTransaction(1);
        assertNotNull(transactions);
    }

    @Test
    void testGetTransactionOfNonStoredUser() {
        assertThrows(WalletNotFoundException.class, () -> transactionService.getTransaction(0));
    }
}