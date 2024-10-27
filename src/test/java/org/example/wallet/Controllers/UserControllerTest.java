package org.example.wallet.Controllers;

import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.CannotCreateUserException;
import org.example.wallet.Exceptions.CustomExceptionHandler;
import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    public void testRegister_Success() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenReturn("user registered successfully");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data").value("user registered successfully"));

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }

    @Test
    public void testRegister_Failure() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenThrow(new CannotCreateUserException("name, password, currencyType cannot be null or empty"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.data").value("name, password, currencyType cannot be null or empty"));

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }

    @Test
    public void testRegister_UsernameAlreadyRegistered() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenThrow(new UsernameAlreadyRegisteredException("username already exists"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(409))
                .andExpect(jsonPath("$.data").value("username already exists"));

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }
}