package org.example.wallet.Controllers;

import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/{id}")
public class WalletController {
    @Autowired
    private WalletService walletService;
    private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@PathVariable int id, @RequestBody WalletDTO walletDTO) {
        try {
            double balance = walletService.deposit(id, walletDTO.getAmount());
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error during deposit", e);
            return ResponseEntity.status(500).body("Error during deposit: " + e.getMessage());
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable int id, @RequestBody WalletDTO walletDTO) {
        try {
            double balance = walletService.withdraw(id, walletDTO.getAmount());
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error during withdraw", e);
            return ResponseEntity.status(500).body("Error during withdraw: " + e.getMessage());
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int id) {
        try {
            double balance = walletService.getBalance(id);
            return ResponseEntity.ok(balance);
        } catch (Exception e) {
            logger.error("Error during balance retrieval", e);
            return ResponseEntity.status(500).body("Error during balance retrieval: " + e.getMessage());
        }
    }
}
