package org.example.wallet.Controllers;

import org.example.wallet.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/{walletId}")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int walletId) {
        return ResponseEntity.ok(transactionService.getTransaction(walletId));
    }
}