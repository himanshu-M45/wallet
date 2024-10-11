package org.example.wallet.Controllers;

import org.example.wallet.Service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class WalletControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testDeposit() throws Exception {
        when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/wallet/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));
    }

    @Test
    void testDepositFail() throws Exception {
        when(walletService.deposit(anyInt(), anyDouble())).thenThrow(new RuntimeException("Deposit failed"));

        mockMvc.perform(post("/wallet/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":100.0}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testWithdraw() throws Exception {
        when(walletService.withdraw(anyInt(), anyDouble())).thenReturn(150.0);

        mockMvc.perform(post("/wallet/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.0"));
    }

    @Test
    void testWithdrawFail() throws Exception {
        when(walletService.withdraw(anyInt(), anyDouble())).thenThrow(new RuntimeException("Withdraw failed"));

        mockMvc.perform(post("/wallet/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":50.0}"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testBalance() throws Exception {
        when(walletService.getBalance(anyInt())).thenReturn(300.0);

        mockMvc.perform(get("/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("300.0"));
    }

    @Test
    void testBalanceFail() throws Exception {
        when(walletService.getBalance(anyInt())).thenThrow(new RuntimeException("Balance retrieval failed"));

        mockMvc.perform(get("/wallet/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isInternalServerError());
    }
}