package org.example.wallet.Service;

import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRegisterUser() {
        User user = new User("John Doe", "password123");

        User savedUser = userService.registerUser(user);
        assertNotNull(savedUser.getId());

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("John Doe", retrievedUser.getName());
        assertEquals("password123", retrievedUser.getPassword());
    }

    @Test
    public void testRegisterUser2() {
        User user = new User("Ethan Hunt", "ethanPass5");

        User savedUser = userService.registerUser(user);
        assertNotNull(savedUser.getId());

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("Ethan Hunt", retrievedUser.getName());
        assertEquals("ethanPass5", retrievedUser.getPassword());
    }

    @Test
    public void testRegisterUser3() {
        User user = new User("Fiona Gallagher", "fionaPass6");

        User savedUser = userService.registerUser(user);
        assertNotNull(savedUser.getId());

        User retrievedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals("Fiona Gallagher", retrievedUser.getName());
        assertEquals("fionaPass6", retrievedUser.getPassword());
    }

    @Test
    public void testFindUserById() {
        User retrieveUser = userService.findUserById(1);
        assertNotNull(retrieveUser);
        assertEquals("John Doe", retrieveUser.getName());
        assertEquals("password123", retrieveUser.getPassword());
    }
}