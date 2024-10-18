package org.example.wallet.Controllers;

import org.example.wallet.DTO.ResponseDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Service.UserService;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/wallets/{walletId}")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @PostMapping("/deposits")
    public ResponseEntity<ResponseDTO<String>> deposit(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.deposits(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "deposit successful", response));
    }

    @PostMapping("/withdrawals")
    public ResponseEntity<ResponseDTO<String>> withdrawal(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.withdrawals(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "withdrawal successful", response));
    }

    @PostMapping("/transfers")
    public ResponseEntity<ResponseDTO<String>> transfer(@PathVariable int userId, @PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(userId, walletId);
        String response = walletService.transfer(walletId, payload.getReceiverWalletId(), payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), response));
    }
}