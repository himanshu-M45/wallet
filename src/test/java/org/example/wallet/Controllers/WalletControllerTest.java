package org.example.wallet.Controllers;

import org.example.wallet.Config.TestSecurityConfig;
import org.example.wallet.Exceptions.*;
import org.example.wallet.Service.UserService;
import org.example.wallet.Service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
@Import(TestSecurityConfig.class)
class WalletControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private WalletService walletService;
    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(walletService, userService);
    }

    @Test
    void testDepositUserNotAuthorized() throws Exception {
        Mockito.doThrow(new UserNotAuthorizedException("user not authorized"))
                .when(userService).isUserAuthorized(anyInt(), anyInt());

        mockMvc.perform(post("/wallets/10/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1, \"amount\":100.0}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("user not authorized"));

        verify(userService, times(1)).isUserAuthorized(anyInt(), anyInt());
    }

    @Test
    void testDepositUserNotAuthenticated() throws Exception {
        Mockito.doThrow(new UserNotAuthenticatedException("user not authenticated"))
                .when(userService).isUserAuthorized(anyInt(), anyInt());

        mockMvc.perform(post("/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":10, \"amount\":100.0}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("user not authenticated"));

        verify(userService, times(1)).isUserAuthorized(anyInt(), anyInt());
    }

    @Test
    public void testDeposit_Success() throws Exception {
        Mockito.when(walletService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1,\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));

        verify(walletService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    public void testDeposit_InvalidAmount() throws Exception {
        Mockito.when(walletService.deposit(anyInt(), anyDouble()))
                .thenThrow(new InvalidAmountEnteredException("Deposit amount cannot be negative or zero"));

        mockMvc.perform(post("/wallets/2/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":2,\"amount\":0.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Deposit amount cannot be negative or zero"));

        verify(walletService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    public void testDeposit_WalletNotFound() throws Exception {
        Mockito.when(walletService.deposit(anyInt(), anyDouble()))
                .thenThrow(new WalletNotFoundException("Wallet not found"));

        mockMvc.perform(post("/wallets/2/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":2,\"amount\":100.0}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found"));

        verify(walletService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    public void testWithdrawal_Success() throws Exception {
        Mockito.when(walletService.withdrawal(anyInt(), anyDouble())).thenReturn(150.0);

        mockMvc.perform(post("/wallets/4/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1,\"amount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.0"));

        verify(walletService, times(1)).withdrawal(anyInt(), anyDouble());
    }

    @Test
    public void testWithdrawal_InvalidAmount() throws Exception {
        when(walletService.withdrawal(anyInt(), anyDouble()))
                .thenThrow(new InvalidAmountEnteredException("Withdrawal amount cannot be negative or zero"));

        mockMvc.perform(post("/wallets/2/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1,\"amount\":-50.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Withdrawal amount cannot be negative or zero"));

        verify(walletService, times(1)).withdrawal(anyInt(), anyDouble());
    }

    @Test
    public void testWithdrawal_InsufficientBalance() throws Exception {
        when(walletService.withdrawal(anyInt(), anyDouble()))
                .thenThrow(new InsufficientBalanceException("Insufficient balance"));

        mockMvc.perform(post("/wallets/2/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1,\"amount\":1000.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient balance"));

        verify(walletService, times(1)).withdrawal(anyInt(), anyDouble());
    }

    @Test
    public void testWithdrawal_WalletNotFound() throws Exception {
        when(walletService.withdrawal(anyInt(), anyDouble()))
                .thenThrow(new WalletNotFoundException("Wallet not found"));

        mockMvc.perform(post("/wallets/2/withdrawal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1,\"amount\":100.0}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found"));

        verify(walletService, times(1)).withdrawal(anyInt(), anyDouble());
    }

    @Test
    public void testGetBalance_Success() throws Exception {
        when(walletService.getBalance(anyInt())).thenReturn(300.0);

        mockMvc.perform(get("/wallets/2/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("300.0"));

        verify(walletService, times(1)).getBalance(anyInt());
    }

    @Test
    public void testGetBalance_WalletNotFound() throws Exception {
        when(walletService.getBalance(anyInt())).thenThrow(new WalletNotFoundException("Wallet not found"));

        mockMvc.perform(get("/wallets/7/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"walletId\":1}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found"));

        verify(walletService, times(1)).getBalance(anyInt());
    }

    @Test
    void testTransferSuccess() throws Exception {
        Mockito.when(walletService.transfer(anyInt(), anyInt(), anyDouble()))
                .thenReturn("transfer successful");

        mockMvc.perform(post("/wallets/1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"receiverId\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().string("transfer successful"));

        verify(walletService, times(1)).transfer(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void testTransferInsufficientFunds() throws Exception {
        Mockito.when(walletService.transfer(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new InsufficientBalanceException("Insufficient balance"));

        mockMvc.perform(post("/wallets/1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":1000.0,\"receiverId\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient balance"));

        verify(walletService, times(1)).transfer(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void testTransferWalletNotFound() throws Exception {
        Mockito.when(walletService.transfer(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new WalletNotFoundException("Wallet not found"));

        mockMvc.perform(post("/wallets/15/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"receiverId\":24}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Wallet not found"));

        verify(walletService, times(1)).transfer(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void testTransferInvalidAmount() throws Exception {
        Mockito.when(walletService.transfer(anyInt(), anyInt(), anyDouble()))
                .thenThrow(new InvalidAmountEnteredException("Invalid amount entered"));

        mockMvc.perform(post("/wallets/1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":0.0,\"receiverId\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid amount entered"));

        verify(walletService, times(1)).transfer(anyInt(), anyInt(), anyDouble());
    }

    @Test
    void testTransferGenericException() throws Exception {
        doThrow(new RuntimeException("An error occurred")).when(walletService).transfer(anyInt(), anyInt(), anyDouble());

        mockMvc.perform(post("/wallets/1/transfers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0,\"receiverId\":2}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred"));

        verify(walletService, times(1)).transfer(anyInt(), anyInt(), anyDouble());
    }
}