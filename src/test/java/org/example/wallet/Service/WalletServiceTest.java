package org.example.wallet.Service;

import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.WalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CurrencyConverterService currencyConverterService;

    @InjectMocks
    private WalletService walletService;

    @Test
    void testDeposits_Success() {
        when(walletRepository.findById(1)).thenReturn(Optional.of(new Wallet()));
        String result = walletService.deposits(1, 100);

        assertEquals("updated balance: 100.0", result);
        verify(transactionService, times(1)).saveWalletTransaction(100, 1, TransactionType.DEPOSIT);
    }

    @Test
    void testDeposits_WalletNotFoundException() {
        when(walletRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> walletService.deposits(1, 100));
    }

    @Test
    void testWithdrawals_Success() {
        when(walletRepository.findById(1)).thenReturn(Optional.of(new Wallet()));
        walletService.deposits(1, 200);
        String result = walletService.withdrawals(1, 100);

        assertEquals("updated balance: 100.0", result);
        verify(transactionService, times(1)).saveWalletTransaction(100, 1, TransactionType.WITHDRAWAL);
    }

    @Test
    void testWithdrawals_WalletNotFoundException() {
        when(walletRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(WalletNotFoundException.class, () -> walletService.withdrawals(1, 100));
    }

    @Test
    void testWithdrawals_InsufficientBalanceException() {
        when(walletRepository.findById(1)).thenReturn(Optional.of(new Wallet()));
        assertThrows(InsufficientBalanceException.class, () -> walletService.withdrawals(1, 100));
    }

    @Test
    void testTransfer_Success() {
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();

        ReflectionTestUtils.setField(senderWallet, "currencyType", CurrencyType.USD);
        ReflectionTestUtils.setField(receiverWallet, "currencyType", CurrencyType.EUR);
        ReflectionTestUtils.setField(senderWallet, "balance", 200.0);

        when(walletRepository.findById(1)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findById(2)).thenReturn(Optional.of(receiverWallet));
        when(currencyConverterService.convertCurrency(100, "USD", "EUR")).thenReturn(85.0);

        String result = walletService.transfer(1, 2, 100);
        assertEquals("Transaction successful", result);
        verify(transactionService, times(1)).saveTransferTransaction(100, 1, 2, TransactionType.TRANSFER);
    }

    @Test
    void testTransfer_WalletNotFoundException() {
        when(walletRepository.findById(1)).thenReturn(Optional.empty());
        when(walletRepository.findById(2)).thenReturn(Optional.of(new Wallet()));
        assertThrows(WalletNotFoundException.class, () -> walletService.transfer(1, 2, 100));
    }

    @Test
    void testTransfer_CurrencyConversionFailure() {
        Wallet senderWallet = new Wallet();
        Wallet receiverWallet = new Wallet();

        ReflectionTestUtils.setField(senderWallet, "currencyType", CurrencyType.USD);
        ReflectionTestUtils.setField(receiverWallet, "currencyType", CurrencyType.EUR);

        when(walletRepository.findById(1)).thenReturn(Optional.of(senderWallet));
        when(walletRepository.findById(2)).thenReturn(Optional.of(receiverWallet));
        when(currencyConverterService.convertCurrency(100, "USD", "EUR")).thenThrow(new RuntimeException("Currency conversion failed"));

        assertThrows(RuntimeException.class, () -> walletService.transfer(1, 2, 100));
    }
}