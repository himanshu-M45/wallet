package org.example.wallet.Controllers;

import org.example.wallet.DTO.TransactionDTO;
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
public class TransactionController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/transact")
    public ResponseEntity<String> transact(@PathVariable int userId, @RequestBody TransactionDTO payload) {
        try {
            // Transfer money between two wallets
            walletService.withdraw(userId, payload.getAmount());
            walletService.deposit(payload.getToUserId(), payload.getAmount());
            return ResponseEntity.ok("Transaction successful");
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
}
