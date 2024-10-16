package org.example.wallet.Models;

import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.CannotCreateUserException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testValidUserInitialization() {
        User user = new User("John Doe", "password123", CurrencyType.INR);
        assertNotNull(user);
    }

    @Test
    void testInvalidUserInitializationEmptyNameAndPassword() {
        assertThrows(CannotCreateUserException.class, () -> new User("", "", null));
    }

    @Test
    void testInvalidUserInitializationNullNameAndPassword() {
        assertThrows(CannotCreateUserException.class, () -> new User(null, null, null));
    }

    @Test
    void testTwoValidUsersShouldNotBeSame() {
        User user1 = new User("Charlie Brown", "charliePass", CurrencyType.INR);
        User user2 = new User("Diana Prince", "dianaPass4", CurrencyType.INR);

        assertNotEquals(user1, user2);
    }
}