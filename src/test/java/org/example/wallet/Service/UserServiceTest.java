package org.example.wallet.Service;

import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testFindById() {
        User user = userService.findById(1);
        assertNotNull(user);
    }

    @Test
    public void testRegister() {
        String response = userService.register("John Doe", "johnPass");
        User retrievedUser = userService.findById(1);
        assertEquals("User registered successfully", response);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister2() {
        String response = userService.register("Ethan Hunt", "ethanPass");
        User retrievedUser = userService.findById(8);
        assertEquals("User registered successfully", response);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister3() {
        String response = userService.register("Fiona Gallagher", "fionaPass");
        User retrievedUser = userService.findById(7);
        assertEquals("User registered successfully", response);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister4() {
        String response = userService.register("Ian Somerhalder", "ianPass");
        User retrievedUser = userService.findById(4);
        assertEquals("User registered successfully", response);
        assertNotNull(retrievedUser);
    }

    @Test
    void testRegister5() {
        String response = userService.register("Charlie Brown", "charliePass");
        User retrievedUser = userService.findById(2);
        assertEquals("User registered successfully", response);
        assertNotNull(retrievedUser);
    }

    @Test
    void testRegisterNewUserWithSameUsername() {
        assertThrows(UsernameAlreadyRegisteredException.class, () -> userService.register("John Doe", "johnPass"));
    }
}