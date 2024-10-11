package org.example.wallet.Controllers;

import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestParam String name, @RequestParam String password) {
        try {
            int userId = userService.registerUser(name, password);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(-1);
        }
    }
}
