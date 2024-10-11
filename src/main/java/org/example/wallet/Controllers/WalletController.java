package org.example.wallet.Controllers;

import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@CrossOrigin
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestParam int userId, @RequestParam double amount) {
        try {
            walletService.deposit(userId, amount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestParam int userId, @RequestParam double amount) {
        try {
            walletService.withdraw(userId, amount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance(@RequestParam int userId) {
        try {
            double balance = walletService.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
