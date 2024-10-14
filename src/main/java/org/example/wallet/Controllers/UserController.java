package org.example.wallet.Controllers;

import org.example.wallet.DTO.UserDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO payload) {
        try {
            int userId = userService.registerUser(payload.getName(), payload.getPassword());
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            logger.error("Error during user creation", e);
            return ResponseEntity.status(500).body("Error during user creation: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Object> deposit(@PathVariable int id, @RequestBody WalletDTO payload) {
        try {
            double balance = userService.deposit(id, payload.getAmount());
            return ResponseEntity.ok(balance);
        } catch (InvalidAmountEnteredException e) {
            logger.error("Invalid amount entered during withdraw", e);
            return ResponseEntity.status(400).body("Invalid amount entered: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error during withdraw", e);
            return ResponseEntity.status(500).body("Error during withdraw: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable int id, @RequestBody WalletDTO payload) {
        try {
            double balance = userService.withdraw(id, payload.getAmount());
            return ResponseEntity.ok(balance);
        } catch (InvalidAmountEnteredException e) {
            logger.error("Invalid amount entered during withdraw", e);
            return ResponseEntity.status(400).body("Invalid amount entered: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            logger.error("Insufficient balance during withdraw", e);
            return ResponseEntity.status(400).body("Insufficient balance: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error during withdraw", e);
            return ResponseEntity.status(500).body("Error during withdraw: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Object> balance(@PathVariable int id) {
        try {
            double balance = userService.getBalance(id);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error during balance retrieval", e);
            return ResponseEntity.status(500).body("Error during balance retrieval: " + e.getMessage());
        }
    }
}
