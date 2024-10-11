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
    public void testRegisterUser4() {
        User user = userService.registerUser("Ian Somerhalder", "ianPass9");

        assertNotNull(user.getId());

        User retrievedUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(retrievedUser);
        assertEquals(user.getId(), retrievedUser.getId());
    }

    @Test
    public void testFindById() {
        User user = userService.findById(1);
        assertNotNull(user);
    }

    @Test
    void testGetBalanceOfStoredUser() {
        User user = userService.findById(1);
        double balance = user.getBalance();
        assertEquals(0, balance);
    }

    @Test
    void testGetBalanceOfStoredUserAndUpdateBalance() {
        User user = userService.findById(1);
        assertEquals(0, user.getBalance());
        user.deposit(100);
        assertEquals(100, user.getBalance());
    }
}