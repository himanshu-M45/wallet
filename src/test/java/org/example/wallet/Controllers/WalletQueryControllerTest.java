package org.example.wallet.Controllers;

import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Service.WalletQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletQueryController.class)
class WalletQueryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletQueryService walletQueryService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(walletQueryService);
    }

    @Test
    public void testGetBalance_Success() throws Exception {
        when(walletQueryService.getBalance(anyInt())).thenReturn(300.0);

        mockMvc.perform(get("/users/2/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("300.0"));

        verify(walletQueryService, times(1)).getBalance(anyInt());
    }

    @Test
    public void testGetBalance_WalletNotFound() throws Exception {
        when(walletQueryService.getBalance(anyInt())).thenThrow(new WalletNotFoundException("Wallet not found"));

        mockMvc.perform(get("/users/7/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found: Wallet not found"));

        verify(walletQueryService, times(1)).getBalance(anyInt());
    }
}