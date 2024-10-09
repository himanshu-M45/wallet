package org.example.wallet.Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testValidUserInitialization() {
        User user = new User();
        user.setName("John Doe");
        user.setPassword("password123");
        assertNotNull(user.getName());
        assertNotNull(user.getPassword());
    }

    @Test
    void testInvalidUserInitialization() {
        User user = new User();
        user.setName(null);
        user.setPassword(null);
        assertNull(user.getName());
        assertNull(user.getPassword());
    }

    @Test
    void testGetNameOfUser() {
        User user = new User();
        user.setName("John Doe");
        assertEquals("John Doe", user.getName());
    }

    @Test
    void testSetUserId() {
        User user = new User();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    void testGetNullUserId() {
        User user = new User();
        assertNull(user.getId());
    }

    @Test
    void testTwoValidUsersShouldNotBeSame() {
        User user1 = new User();
        user1.setName("John Doe");
        user1.setPassword("password123");
        user1.setId(1);

        User user2 = new User();
        user2.setName("John Doe");
        user2.setPassword("password123");
        user2.setId(2);

        assertNotEquals(user1, user2);
    }

    @Test
    void testTwoInvalidUsersShouldNotBeSame() {
        User firstUser = new User();
        User secondUser = new User();

        assertNotEquals(firstUser, secondUser);
    }

    @Test
    void testNullWalletWhenUserIsDummy() {
        User user = new User();
        assertNull(user.getWallet());
    }

    @Test
    void testWalletNotNullWhenUserIsNotDummy() {
        User user = new User();
        user.setName("John Doe");
        user.setPassword("password123");
        user.setId(3);
        user.ownWallet();
        assertNotNull(user.getWallet());
    }

    @Test
    void testUserWalletBalance() {
        User user = new User();
        user.setName("John Doe");
        user.setPassword("password123");
        user.setId(3);
        user.ownWallet();

        Wallet wallet = user.getWallet();
        wallet.deposit(100);

        assertEquals(100, wallet.getBalance());
        assertEquals(100, user.getBalance());
    }
}