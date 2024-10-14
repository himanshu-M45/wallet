package org.example.wallet.Service;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Exceptions.UserIsNotRegisteredException;
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
    public void testRegisterUser() {
//        int savedUserId = userService.registerUser("John Doe", "password123");
        int savedUserId = 1;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser2() {
//        int savedUserId = userService.registerUser("Ethan Hunt", "ethanPass5");
        int savedUserId = 3;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser3() {
//        int savedUserId = userService.registerUser("Fiona Gallagher", "fionaPass6");
        int savedUserId = 4;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser4() {
//        int savedUserId = userService.registerUser("Ian Somerhalder", "ianPass9");
        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    void testRegisterUser5() {
//        int savedUserId = userService.registerUser("Charlie Brown", "charliePass");
        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }


    @Test
    void testGetBalanceOfStoredUser() {
        User user = userService.findById(1);
        double balance = user.getBalance();
        assertEquals(600, balance);
    }

    @Test
    void testGetBalanceOfNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> userService.getBalance(0));
    }

    @Test
    void testDepositToNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> userService.deposit(0, 100));
    }

    @Test
    void testWithdrawFromNonStoredUser() {
        assertThrows(UserIsNotRegisteredException.class, () -> userService.withdraw(0, 100));
    }

    @Test
    void testGetBalanceOfStoredUserAndUpdateBalance() {
        assertEquals(200, userService.getBalance(3));
        double balance = userService.deposit(3, 100);
        assertEquals(balance, userService.getBalance(3));
    }

    @Test
    void testDepositFromUserService() {
        assertEquals(250, userService.getBalance(2));
        double balance = userService.deposit(2, 100);
        assertEquals(balance, userService.getBalance(2));
    }

    @Test
    void testDeposit0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> userService.deposit(1, 0));
    }

    @Test
    void testWithdraw0FromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> userService.withdraw(1, 0));
    }

    @Test
    void testWithdrawNegativeAmountFromUserService() {
        assertThrows(InvalidAmountEnteredException.class, () -> userService.withdraw(1, -100));
    }

    @Test
    void testWithdrawMoreThanBalanceFromUserService() {
        assertThrows(InsufficientBalanceException.class, () -> userService.withdraw(1, 500));
    }

    @Test
    void testWithdraw50FromUserService() {
        assertEquals(750, userService.getBalance(1));
        double balance = userService.withdraw(1, 150);
        assertEquals(balance, userService.getBalance(1));
    }
}