package org.example.wallet.Controllers;

import org.example.wallet.DTO.ResponseDTO;
import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.Service.TransactionService;
import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/{walletId}/transaction")
    public ResponseEntity<Object> transactions(@PathVariable int walletId, @RequestBody TransactionDTO payload) {
        userService.isUserAuthorized(payload.getUserId(), walletId);
        return ResponseEntity.ok(transactionService.getTransactionsByWalletId(walletId));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> allTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}