package org.example.wallet.Controllers;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Service.TransactionService;
import org.example.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;


class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testGetTransactionsByWalletId() throws Exception {
        int userId = 1;
        int walletId = 1;

        when(transactionService.getTransactionsByWalletId(walletId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/{userId}/wallets/{walletId}/transactions", userId, walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(userService, times(1)).isUserAuthorized(userId, walletId);
        verify(transactionService, times(1)).getTransactionsByWalletId(walletId);
    }

    @Test
    void testGetTransactionsByTransferType() throws Exception {
        int userId = 1;
        int walletId = 1;
        TransactionType type = TransactionType.TRANSFER;

        when(transactionService.getTransactionsByType(walletId, type)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/users/{userId}/wallets/{walletId}/transactions", userId, walletId)
                        .param("type", type.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(userService, times(1)).isUserAuthorized(userId, walletId);
        verify(transactionService, times(1)).getTransactionsByType(walletId, type);
    }
}