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

//    @Test
//    void testUserWalletBalance() {
//        User user = new User("Ian Somerhalder", "ianPass9");
//        Wallet wallet = user.getWallet();
//        wallet.deposit(100);
//
//        assertEquals(100, wallet.getBalance());
//    }
//
//    @Test
//    void testUserWalletAdd0Balance() {
//        User user = new User("Jessica Jones", "jessicaPass10");
//        Wallet wallet = user.getWallet();
//        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(0));
//    }
//
//    @Test
//    void testUserWalletAddNegativeBalance() {
//        User user = new User("Kevin Hart", "kevinPass11");
//        Wallet wallet = user.getWallet();
//        assertThrows(IllegalArgumentException.class, () -> wallet.deposit(-100));
//    }
//
//    @Test
//    void testUserWalletWithdraw0Balance() {
//        User user = new User("Laura Palmer", "lauraPass12");
//        Wallet wallet = user.getWallet();
//        assertThrows(IllegalArgumentException.class, () -> wallet.withdraw(0));
//    }
//
//    @Test
//    void testUserWalletWithdraw100WhileBalanceIs50() {
//        User user = new User("Michael Scott", "michaelPass13");
//
//        Wallet wallet = user.getWallet();
//        wallet.deposit(50);
//
//        assertThrows(InsufficientBalanceException.class, () -> wallet.withdraw(100));
//    }
//
//    @Test
//    void testUserWalletWithdraw150From550AndUpdateBalance350() {
//        User user = new User("Nancy Drew", "nancyPass14");
//
//        Wallet wallet = user.getWallet();
//        wallet.deposit(500);
//        wallet.withdraw(150);
//
//        assertEquals(350, wallet.getBalance());
//    }
}