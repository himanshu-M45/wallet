package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private UserService userService;

    public Wallet findWalletById(int id) {
        return walletRepository.findById(id).orElse(null);
    }

    @Transactional
    public Double deposit(int userId, double amount) {
        int walletId = userService.findWalletId(userId);
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            return wallet.deposit(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public Double withdraw(int userId, double amount) {
        int walletId = userService.findWalletId(userId);
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            return wallet.withdrawal(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    public double getBalance(int userId) {
        int walletId = userService.findWalletId(userId);
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            return wallet.getBalance();
        }
        throw new WalletNotFoundException("Wallet not found");
    }
}
