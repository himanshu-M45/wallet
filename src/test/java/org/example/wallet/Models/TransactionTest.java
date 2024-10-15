package org.example.wallet.Models;

import org.example.wallet.Enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Mock
    private Wallet mockWallet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testTransactionInitialization() {
//        Transaction transaction = new Transaction("Test message", TransactionType.DEPOSIT, mockWallet);
//        assertEquals("Test message", transaction.getMessage());
//        assertEquals(TransactionType.DEPOSIT, transaction.getTransactionType());
//        assertEquals(mockWallet, transaction.getWallet());
//    }
}