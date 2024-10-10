package org.example.wallet.Service;

import org.example.wallet.Models.User;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        User user = userService.registerUser("John Doe", "password123");

        assertNotNull(user.getId());

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testRegisterUser2() {
        User user = userService.registerUser("Ethan Hunt", "ethanPass5");

        assertNotNull(user.getId());

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testRegisterUser3() {
        User user = userService.registerUser("Fiona Gallagher", "fionaPass6");

        assertNotNull(user.getId());

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testFindById() {
        Optional<User> retrieveUser = userService.findById(1);
        assertNotNull(retrieveUser);
    }

    @Test
    void testGetBalanceOfStoredUser() {
        Optional<User> userOptional = userService.findById(1);
        assertTrue(userOptional.isPresent(), "User should be present");
        User user = userOptional.get();
        double balance = user.getBalance();
        assertEquals(0, balance);
    }

    @Test
    void testGetBalanceOfStoredUserAndUpdateBalance() {
        Optional<User> userOptional = userService.findById(1);
        assertTrue(userOptional.isPresent(), "User should be present");
        User user = userOptional.get();
        assertEquals(0, user.getBalance());
        user.deposit(100);
        assertEquals(100, user.getBalance());
    }
}