package org.example.wallet.Controllers;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Service.TransactionService;
import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallets/{walletId}")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int userId, @PathVariable int walletId, @RequestParam(required = false) TransactionType type) {
        userService.isUserAuthorized(userId, walletId);
        if (type != null) {
            return ResponseEntity.ok(transactionService.getTransactionsByType(walletId, type));
        }
        return ResponseEntity.ok(transactionService.getTransactionsByWalletId(walletId));
    }
}