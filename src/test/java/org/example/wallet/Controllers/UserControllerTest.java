package org.example.wallet.Controllers;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        Mockito.reset(userService);
    }

    @Test
    public void testRegister_Success() throws Exception {
        Mockito.when(userService.registerUser("testUser", "testPassword")).thenReturn(1);

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(userService, times(1)).registerUser("testUser", "testPassword");
    }

    @Test
    public void testRegister_Failure() throws Exception {
        Mockito.when(userService.registerUser("testUser", "testPassword")).thenThrow(new RuntimeException("Registration failed"));

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error during user creation: Registration failed"));

        verify(userService, times(1)).registerUser("testUser", "testPassword");
    }

    @Test
    public void testDeposit_Success() throws Exception {
        Mockito.when(userService.deposit(anyInt(), anyDouble())).thenReturn(200.0);

        mockMvc.perform(post("/user/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("200.0"));

        verify(userService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    public void testDeposit_Failure() throws Exception {
        Mockito.when(userService.deposit(anyInt(), anyDouble())).thenThrow(new InvalidAmountEnteredException("Withdrawal amount cannot be negative or zero"));

        mockMvc.perform(post("/user/2/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":2,\"amount\":0.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid amount entered: Withdrawal amount cannot be negative or zero"));

        verify(userService, times(1)).deposit(anyInt(), anyDouble());
    }

    @Test
    public void testWithdraw_Success() throws Exception {
        Mockito.when(userService.withdraw(anyInt(), anyDouble())).thenReturn(150.0);

        mockMvc.perform(post("/user/4/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":50.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("150.0"));

        verify(userService, times(1)).withdraw(anyInt(), anyDouble());
    }

    @Test
    public void testWithdraw_Failure() throws Exception {
        when(userService.withdraw(anyInt(), anyDouble())).thenThrow(new InvalidAmountEnteredException("Withdrawal amount cannot be negative or zero"));

        mockMvc.perform(post("/user/2/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":-50.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid amount entered: Withdrawal amount cannot be negative or zero"));

        verify(userService, times(1)).withdraw(anyInt(), anyDouble());

        reset(userService);

        when(userService.withdraw(anyInt(), anyDouble())).thenThrow(new InsufficientBalanceException("Insufficient balance"));

        mockMvc.perform(post("/user/2/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"amount\":1000.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Insufficient balance: Insufficient balance"));

        verify(userService, times(1)).withdraw(anyInt(), anyDouble());
    }

    @Test
    public void testBalance_Success() throws Exception {
        when(userService.getBalance(anyInt())).thenReturn(300.0);

        mockMvc.perform(get("/user/2/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("300.0"));

        verify(userService, times(1)).getBalance(anyInt());
    }

    @Test
    public void testBalance_Failure() throws Exception {
        when(userService.getBalance(anyInt())).thenThrow(new RuntimeException("Balance retrieval failed"));

        mockMvc.perform(get("/user/7/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error during balance retrieval: Balance retrieval failed"));

        verify(userService, times(1)).getBalance(anyInt());
    }
}