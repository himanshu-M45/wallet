package org.example.wallet.Controllers;


import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(walletService);
    }

    @Test
    void testTransactSuccess() throws Exception {
        Mockito.when(walletService.withdraw(anyInt(), anyDouble())).thenReturn(200.0);
        Mockito.when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/users/1/wallet/transact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"toUserId\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transaction successful"));

        verify(walletService, times(1)).withdraw(anyInt(), anyDouble());
        verify(walletService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    void testTransactInsufficientFunds() throws Exception {
        Mockito.when(walletService.withdraw(anyInt(), anyDouble())).thenThrow(new InsufficientBalanceException("Insufficient funds"));
        Mockito.when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/users/1/wallet/transact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1000.0,\"toUserId\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient funds"));

        verify(walletService, times(1)).withdraw(anyInt(), anyDouble());
        verify(walletService, times(0)).deposit(anyInt(), anyDouble());
    }

    @Test
    void testTransactWalletNotFound() throws Exception {
        Mockito.when(walletService.withdraw(anyInt(), anyDouble())).thenThrow(new WalletNotFoundException("Wallet not found"));
        Mockito.when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/users/15/wallet/transact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"toUserId\":24}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found: Wallet not found"));

        verify(walletService, times(1)).withdraw(anyInt(), anyDouble());
        verify(walletService, times(0)).deposit(anyInt(), anyDouble());
    }

    @Test
    void testTransactInvalidAmount() throws Exception {
        Mockito.when(walletService.withdraw(anyInt(), anyDouble())).thenThrow(new InvalidAmountEnteredException("Invalid amount entered"));
        Mockito.when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/users/1/wallet/transact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":0.0,\"toUserId\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid amount entered: Invalid amount entered"));

        verify(walletService, times(1)).withdraw(anyInt(), anyDouble());
        verify(walletService, times(0)).deposit(anyInt(), anyDouble());
    }

    @Test
    void testTransactGenericException() throws Exception {
        doThrow(new RuntimeException("An error occurred")).when(walletService).withdraw(anyInt(), anyDouble());

        mockMvc.perform(post("/users/1/wallet/transact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"toUserId\":2}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred"));

        verify(walletService, times(1)).withdraw(anyInt(), anyDouble());
    }
}