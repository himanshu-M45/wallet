package org.example.wallet.Service;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Exceptions.InvalidTransactionTypeException;
import org.example.wallet.Exceptions.NoTransactionFoundException;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Models.TransferTransaction;
import org.example.wallet.Models.WalletTransaction;
import org.example.wallet.Repositorys.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTransactionsByWalletId() {
        int walletId = 1;
        List<Transaction> walletTransactions = List.of(new WalletTransaction());
        List<Transaction> transferTransactions = List.of(new TransferTransaction());

        when(transactionRepository.findWalletTransactionsByWalletId(walletId)).thenReturn(walletTransactions);
        when(transactionRepository.findTransferTransactionsByWalletId(walletId)).thenReturn(transferTransactions);

        List<Transaction> transactions = transactionService.getTransactionsByWalletId(walletId);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        verify(transactionRepository, times(1)).findWalletTransactionsByWalletId(walletId);
        verify(transactionRepository, times(1)).findTransferTransactionsByWalletId(walletId);
    }

    @Test
    void testGetTransactionsByType_Transfer() {
        int walletId = 1;
        TransactionType transactionType = TransactionType.TRANSFER;
        List<Transaction> transferTransactions = List.of(new TransferTransaction());

        when(transactionRepository.findTransferTransactionsByTypeAndWalletId(transactionType, walletId)).thenReturn(transferTransactions);

        List<Transaction> transactions = transactionService.getTransactionsByType(walletId, transactionType);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        verify(transactionRepository, times(1)).findTransferTransactionsByTypeAndWalletId(transactionType, walletId);
    }

    @Test
    void testGetTransactionsByType_Deposit() {
        int walletId = 1;
        TransactionType transactionType = TransactionType.DEPOSIT;
        List<Transaction> depositTransactions = List.of(new WalletTransaction());

        when(transactionRepository.findWalletTransactionsByTypeAndWalletId(transactionType, walletId)).thenReturn(depositTransactions);

        List<Transaction> transactions = transactionService.getTransactionsByType(walletId, transactionType);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        verify(transactionRepository, times(1)).findWalletTransactionsByTypeAndWalletId(transactionType, walletId);
    }

    @Test
    void testGetTransactionsByType_Withdrawals() {
        int walletId = 1;
        TransactionType transactionType = TransactionType.WITHDRAWAL;
        List<Transaction> withdrawalTransactions = List.of(new WalletTransaction());

        when(transactionRepository.findWalletTransactionsByTypeAndWalletId(transactionType, walletId)).thenReturn(withdrawalTransactions);

        List<Transaction> transactions = transactionService.getTransactionsByType(walletId, transactionType);
        assertNotNull(transactions);
        assertFalse(transactions.isEmpty());

        verify(transactionRepository, times(1)).findWalletTransactionsByTypeAndWalletId(transactionType, walletId);
    }

    @Test
    void testGetTransactionsByType_InvalidType() {
        assertThrows(InvalidTransactionTypeException.class, () -> {
            transactionService.getTransactionsByType(1, null);
        });
    }

    @Test
    void testGetTransactionsByType_NoTransactionsFound() {
        int walletId = 1;
        TransactionType transactionType = TransactionType.TRANSFER;

        when(transactionRepository.findTransferTransactionsByTypeAndWalletId(transactionType, walletId)).thenReturn(new ArrayList<>());

        assertThrows(NoTransactionFoundException.class, () -> {
            transactionService.getTransactionsByType(walletId, transactionType);
        });
    }
}