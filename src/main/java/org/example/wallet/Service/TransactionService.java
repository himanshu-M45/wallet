package org.example.wallet.Service;

import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Models.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private WalletService walletService;

    public double getBalance(int walletId) {
        Wallet wallet = walletService.findWalletById(walletId);
        if (wallet != null) {
            return wallet.getBalance();
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    public List<Transaction> getTransactions(int walletId) {
        Wallet wallet = walletService.findWalletById(walletId);
        if (wallet != null) {
            return wallet.getTransactions();
        }
        throw new WalletNotFoundException("Wallet not found");
    }
}
