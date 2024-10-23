package org.example.wallet.Controllers;

import org.example.wallet.DTO.ResponseDTO;
import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Service.TransactionService;
import org.example.wallet.Service.UserService;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/wallets/{walletId}")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @PostMapping("/deposits")
    public ResponseEntity<ResponseDTO<String>> deposit(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.deposits(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), response));
    }

    @PostMapping("/withdrawals")
    public ResponseEntity<ResponseDTO<String>> withdrawal(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.withdrawals(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), response));
    }

    @PostMapping("/transfers")
    public ResponseEntity<ResponseDTO<String>> transfer(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.transfer(walletId, payload.getReceiverWalletId(), payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), response));
    }

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(
            @PathVariable int userId,
            @PathVariable int walletId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        userService.isUserAuthorized(userId, walletId);
        List<Transaction> transactions;

        if (type != null && !type.isEmpty()) {
            transactions = transactionService.getTransactionsByType(walletId, type);
        } else {
            transactions = transactionService.getTransactionsByWalletId(walletId);
        }

        if (sortBy != null && sortOrder != null && !sortBy.isEmpty() && !sortOrder.isEmpty()) {
            transactions = transactionService.getSortedTransactions(transactions, sortBy, sortOrder);
        }

        List<TransactionDTO> transactionDTOs = transactions.stream()
                .map(transactionService::convertToDTO)
                .toList();
        return ResponseEntity.ok(transactionDTOs);
    }
}