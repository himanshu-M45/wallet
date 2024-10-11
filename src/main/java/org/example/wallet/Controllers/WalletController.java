package org.example.wallet.Controllers;

import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<Double> deposit(@RequestBody Map<String, Object> depositMap) {
        try {
            int userId = (Integer) depositMap.get("userId");
            double amount = (Double) depositMap.get("amount");
            double balance = walletService.deposit(userId, amount);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Double> withdraw(@RequestBody Map<String, Object> withdrawMap) {
        try {
            int userId = (Integer) withdrawMap.get("userId");
            double amount = (Double) withdrawMap.get("amount");
            double balance = walletService.withdraw(userId, amount);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Double> balance(@RequestBody Map<String, Object> balanceMap) {
        try {
            int userId = (Integer) balanceMap.get("userId");
            double balance = walletService.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
