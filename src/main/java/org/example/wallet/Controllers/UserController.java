package org.example.wallet.Controllers;

import org.example.wallet.DTO.UserDTO;
import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO payload) {
        int userId = userService.register(payload.getName(), payload.getPassword());
        return ResponseEntity.ok(userId);
    }
}
