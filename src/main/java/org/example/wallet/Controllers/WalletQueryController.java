package org.example.wallet.Controllers;

import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Service.WalletQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{walletId}/wallet")
public class WalletQueryController {
    @Autowired
    private WalletQueryService walletQueryService;

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int walletId) {
        double balance = walletQueryService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int walletId) {
        return ResponseEntity.ok(walletQueryService.getTransactions(walletId));
    }
}