package org.example.wallet.Service;

import org.example.wallet.Exceptions.InsufficientBalanceException;
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
        assertEquals(400, balance);
    }

    @Test
    void testGetBalanceOfStoredUserAndUpdateBalance() {
        assertEquals(0, userService.getBalance(2));
        userService.deposit(2, 100);
        assertEquals(100, userService.getBalance(2));
    }

    @Test
    void testDepositFromUserService() {
        assertEquals(300, userService.getBalance(1));
        userService.deposit(1, 100);
        assertEquals(400, userService.getBalance(1));
    }

    @Test
    void testDeposit0FromUserService() {
        assertThrows(IllegalArgumentException.class, () -> userService.deposit(1, 0));
    }

    @Test
    void testWithdraw0FromUserService() {
        assertThrows(IllegalArgumentException.class, () -> userService.withdraw(1, 0));
    }

    @Test
    void testWithdrawNegativeAmountFromUserService() {
        assertThrows(IllegalArgumentException.class, () -> userService.withdraw(1, -100));
    }

    @Test
    void testWithdrawMoreThanBalanceFromUserService() {
        assertThrows(InsufficientBalanceException.class, () -> userService.withdraw(1, 500));
    }

    @Test
    void testWithdraw50FromUserService() {
        assertEquals(350, userService.getBalance(1));
        userService.withdraw(1, 50);
        assertEquals(300, userService.getBalance(1));
    }
}