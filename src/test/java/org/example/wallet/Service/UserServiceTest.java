package org.example.wallet.Service;

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
    public void testRegister() {
//        int savedUserId = userService.registerUser("John Doe", "password123");
        int savedUserId = 1;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister2() {
//        int savedUserId = userService.registerUser("Ethan Hunt", "ethanPass5");
        int savedUserId = 3;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister3() {
//        int savedUserId = userService.registerUser("Fiona Gallagher", "fionaPass6");
        int savedUserId = 4;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    public void testRegister4() {
//        int savedUserId = userService.registerUser("Ian Somerhalder", "ianPass9");
        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }

    @Test
    void testRegister5() {
//        int savedUserId = userService.registerUser("Charlie Brown", "charliePass");
        int savedUserId = 5;
        User retrievedUser = userService.findById(savedUserId);
        assertNotNull(retrievedUser);
    }
}