package org.example.wallet.Models;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testValidUserInitialization() {
        User user = new User("John Doe", "password123");
        assertNotNull(user);
    }

    @Test
    void testInvalidUserInitializationEmptyNameAndPassword() {
        assertThrows(IllegalArgumentException.class, () -> new User("", ""));
    }

    @Test
    void testInvalidUserInitializationNullNameAndPassword() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, null));
    }

    @Test
    void testTwoValidUsersShouldNotBeSame() {
        User user1 = new User("Charlie Brown", "charliePass");
        User user2 = new User("Diana Prince", "dianaPass4");

        assertNotEquals(user1, user2);
    }

    @Test
    void testUserWalletBalance() {
        User user = new User("Ian Somerhalder", "ianPass9");
        user.deposit(100);

        assertEquals(100, user.getBalance());
    }

    @Test
    void testUserWalletAdd0Balance() {
        User user = new User("Jessica Jones", "jessicaPass10");
        assertThrows(IllegalArgumentException.class, () -> user.deposit(0));
    }

    @Test
    void testUserWalletAddNegativeBalance() {
        User user = new User("Kevin Hart", "kevinPass11");
        assertThrows(IllegalArgumentException.class, () -> user.deposit(-100));
    }

    @Test
    void testUserWalletWithdraw0Balance() {
        User user = new User("Laura Palmer", "lauraPass12");
        assertThrows(IllegalArgumentException.class, () -> user.withdraw(0));
    }

    @Test
    void testUserWalletWithdraw100WhileBalanceIs50() {
        User user = new User("Michael Scott", "michaelPass13");
        user.deposit(50);
        assertThrows(InsufficientBalanceException.class, () -> user.withdraw(100));
    }

    @Test
    void testUserWalletWithdraw150From550AndUpdateBalance350() {
        User user = new User("Nancy Drew", "nancyPass14");
        user.deposit(500);
        double balance = user.withdraw(150);
        assertEquals(balance, user.getBalance());
    }
}