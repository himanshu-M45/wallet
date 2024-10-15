package org.example.wallet.Controllers;

import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Service.WalletQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallet")
public class WalletQueryController {
    @Autowired
    private WalletQueryService walletQueryService;

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int userId) {
        try {
            double balance = walletQueryService.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(404).body("Wallet not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during balance retrieval: " + e.getMessage());
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(walletQueryService.getTransactions(userId));
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(404).body("Wallet not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during transaction retrieval: " + e.getMessage());
        }
    }
}