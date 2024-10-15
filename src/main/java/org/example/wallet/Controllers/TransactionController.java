package org.example.wallet.Controllers;

import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.Exceptions.UserNotAuthorizedException;
import org.example.wallet.Service.TransactionService;
import org.example.wallet.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets/{walletId}")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @GetMapping("/transactions")
    public ResponseEntity<Object> transactions(@PathVariable int walletId, @RequestBody TransactionDTO payload) {
        if (userService.isUserAuthorized(payload.getUserId(), walletId)) {
            return ResponseEntity.ok(transactionService.getTransaction(walletId));
        }
        throw new UserNotAuthorizedException("User not authorized to transact using this wallet");
    }
}