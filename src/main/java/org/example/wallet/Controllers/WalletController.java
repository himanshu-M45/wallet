package org.example.wallet.Controllers;

import org.example.wallet.DTO.ResponseDTO;
import org.example.wallet.DTO.TransactionDTO;
import org.example.wallet.DTO.WalletDTO;
import org.example.wallet.Service.UserService;
import org.example.wallet.Service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ResponseDTO<Double>> deposit(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(payload.getUserId(), walletId);
        double balance = walletService.deposit(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "deposit successful", balance));
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<ResponseDTO<Double>> withdrawal(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(payload.getUserId(), walletId);
        double balance = walletService.withdrawal(walletId, payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "withdrawal successful", balance));
    }

    @PostMapping("/transfers")
    public ResponseEntity<ResponseDTO<String>> transfer(@PathVariable int walletId, @RequestBody WalletDTO payload) {
        userService.isUserAuthorized(payload.getUserId(), walletId);
        String response = walletService.transfer(walletId, payload.getReceiverWalletId(), payload.getAmount());
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), response));
    }

    @GetMapping("/balance")
    public ResponseEntity<ResponseDTO<Double>> balance(@PathVariable int walletId, @RequestBody TransactionDTO payload) {
        userService.isUserAuthorized(payload.getUserId(), walletId);
        double balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.OK.value(), "balance fetched successfully", balance));
    }
}