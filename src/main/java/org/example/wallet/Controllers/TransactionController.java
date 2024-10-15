package org.example.wallet.Controllers;

import org.example.wallet.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{walletId}/wallets")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int walletId) {
        double balance = transactionService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int walletId) {
        return ResponseEntity.ok(transactionService.getTransactions(walletId));
    }
}