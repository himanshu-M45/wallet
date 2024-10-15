package org.example.wallet.Controllers;

import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/{walletId}")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        double balance = walletService.deposit(walletId, payload.getAmount());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Object> withdrawal(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        double balance = walletService.withdrawal(walletId, payload.getAmount());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/transact")
    public ResponseEntity<String> transact(@PathVariable int walletId, @RequestBody TransactionDTO payload) {
        String response = walletService.transact(walletId, payload.getReceiverId(), payload.getAmount());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int walletId) {
        double balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}