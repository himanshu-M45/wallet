package org.example.wallet.Controllers;

import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@PathVariable int userId, @RequestBody WalletDTO payload) {
        try {
            double balance = walletService.deposit(userId, payload.getAmount());
            return ResponseEntity.ok(balance);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(404).body("Wallet not found: " + e.getMessage());
        } catch (InvalidAmountEnteredException e) {
            return ResponseEntity.status(400).body("Invalid amount entered: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during withdraw: " + e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Object> withdrawal(@PathVariable int userId, @RequestBody WalletDTO payload) {
        try {
            double balance = walletService.withdrawal(userId, payload.getAmount());
            return ResponseEntity.ok(balance);
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(404).body("Wallet not found: " + e.getMessage());
        } catch (InvalidAmountEnteredException e) {
            return ResponseEntity.status(400).body("Invalid amount entered: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(400).body("Insufficient balance: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during withdraw: " + e.getMessage());
        }
    }

    @PostMapping("/transact")
    public ResponseEntity<String> transact(@PathVariable int userId, @RequestBody TransactionDTO payload) {
        try {
            // Transfer money between two wallets
            String response = walletService.transact(userId, payload.getReceiverId(), payload.getAmount());
            return ResponseEntity.ok(response);
        } catch (InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet not found: " + e.getMessage());
        } catch (InvalidAmountEnteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid amount entered: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/balance")
    public ResponseEntity<Object> balance(@PathVariable int userId) {
        try {
            double balance = walletService.getBalance(userId);
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
            return ResponseEntity.ok(walletService.getTransactions(userId));
        } catch (WalletNotFoundException e) {
            return ResponseEntity.status(404).body("Wallet not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during transaction retrieval: " + e.getMessage());
        }
    }
}