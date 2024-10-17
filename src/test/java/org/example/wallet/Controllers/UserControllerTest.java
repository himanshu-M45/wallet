package org.example.wallet.Controllers;

import org.example.wallet.Config.TestSecurityConfig;
import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.CannotCreateUserException;
import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
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
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenReturn("user registered successfully");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.message").value("user registered successfully"));

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }

    @Test
    public void testRegister_Failure() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenThrow(new CannotCreateUserException("name, password, currencyType cannot be null or empty"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("name, password, currencyType cannot be null or empty"))
                .andExpect(jsonPath("$.data").isEmpty());

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }

    @Test
    public void testRegister_UsernameAlreadyRegistered() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword", CurrencyType.INR))
                .thenThrow(new UsernameAlreadyRegisteredException("username already exists"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\", \"currencyType\": \"INR\"}"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(409))
                .andExpect(jsonPath("$.message").value("username already exists"))
                .andExpect(jsonPath("$.data").isEmpty());

        verify(userService, times(1)).register("testUser", "testPassword", CurrencyType.INR);
    }
}