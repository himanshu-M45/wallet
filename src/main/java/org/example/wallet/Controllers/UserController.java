package org.example.wallet.Controllers;

import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Integer> register(@RequestBody Map<String, String> userMap) {
        try {
            int userId = userService.registerUser(userMap.get("name"), userMap.get("password"));
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(-1);
        }
    }
}
