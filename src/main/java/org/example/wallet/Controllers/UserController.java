package org.example.wallet.Controllers;

import org.example.wallet.DTO.ResponseDTO;
import org.example.wallet.DTO.UserDTO;
import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ResponseDTO<String>> register(@RequestBody UserDTO payload) {
        String response = userService.register(payload.getName(), payload.getPassword(), payload.getCurrencyType());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.CREATED.value(), response));
    }
}
