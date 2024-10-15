package org.example.wallet.Controllers;

import org.example.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
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
        Mockito.when(userService.register("testUser", "testPassword")).thenReturn("User registered successfully");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(userService, times(1)).register("testUser", "testPassword");
    }

    @Test
    public void testRegister_Failure() throws Exception {
        Mockito.when(userService.register("testUser", "testPassword")).thenThrow(new RuntimeException("An error occurred"));

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred"));

        verify(userService, times(1)).register("testUser", "testPassword");
    }
}