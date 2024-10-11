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
    private UserService userService;

    @Test
    public void testFindById() {
        User user = userService.findById(1);
        assertNotNull(user);
    }

    @Test
    public void testRegisterUser() {
        int savedUserId = userService.registerUser("John Doe", "password123");
//        int savedUserId = 1;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser2() {
        int savedUserId = userService.registerUser("Ethan Hunt", "ethanPass5");
//        int savedUserId = 3;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser3() {
        int savedUserId = userService.registerUser("Fiona Gallagher", "fionaPass6");
//        int savedUserId = 4;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegisterUser4() {
        int savedUserId = userService.registerUser("Ian Somerhalder", "ianPass9");
//        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    void testRegisterUser5() {
        int savedUserId = userService.registerUser("Charlie Brown", "charliePass");
//        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }


    @Test
    void testGetBalanceOfStoredUser() {
        User user = userService.findById(1);
        double balance = user.getBalance();
        assertEquals(400, balance);
    }
}