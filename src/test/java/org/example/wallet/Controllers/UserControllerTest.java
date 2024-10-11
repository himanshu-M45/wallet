package org.example.wallet.Controllers;

import org.example.wallet.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "testUser");
        userMap.put("password", "testPassword");

        when(userService.registerUser("testUser", "testPassword")).thenReturn(1);

        ResponseEntity<Integer> response = userController.register(userMap);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody());
    }

    @Test
    public void testRegister_Failure() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "testUser");
        userMap.put("password", "testPassword");

        when(userService.registerUser("testUser", "testPassword")).thenThrow(new RuntimeException("Registration failed"));

        ResponseEntity<Integer> response = userController.register(userMap);

        assertEquals(500, response.getStatusCode().value());
        assertEquals(-1, response.getBody());
    }
}