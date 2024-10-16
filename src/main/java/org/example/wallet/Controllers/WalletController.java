package org.example.wallet.Controllers;

import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Exceptions.UserNotAuthorizedException;
import org.example.wallet.Service.UserService;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/{walletId}")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        if (userService.isUserAuthorized(payload.getUserId(), walletId)) {
            double balance = walletService.deposit(walletId, payload.getAmount());
            return ResponseEntity.ok(balance);
        }
        throw new UserNotAuthorizedException("User not authorized to deposit to this wallet");
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Object> withdrawal(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        if (userService.isUserAuthorized(payload.getUserId(), walletId)) {
            double balance = walletService.withdrawal(walletId, payload.getAmount());
            return ResponseEntity.ok(balance);
        }
        throw new UserNotAuthorizedException("User not authorized to withdraw from this wallet");
    }

    @PostMapping("/transfers")
    public ResponseEntity<String> transfer(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        if (userService.isUserAuthorized(payload.getUserId(), walletId)) {
            String response = walletService.transfer(walletId, payload.getReceiverWalletId(), payload.getAmount());
            return ResponseEntity.ok(response);
        }
        throw new UserNotAuthorizedException("User not authorized to transact using this wallet");
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int walletId, @RequestBody TransactionDTO payload) {
        if (userService.isUserAuthorized(payload.getUserId(), walletId)) {
            double balance = walletService.getBalance(walletId);
            return ResponseEntity.ok(balance);
        }
        throw new UserNotAuthorizedException("User not authorized to check balance of this wallet");
    }
}