package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Enums.TransactionType;
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
    private TransactionService transactionService;

    public Wallet findWalletById(int id) {
        return walletRepository.findById(id).orElse(null);
    }

    @Transactional
    public Double deposit(int walletId, double amount) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            transactionService.saveTransaction(amount + " added to balance", TransactionType.DEPOSIT, walletId);
            return wallet.deposit(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public Double withdrawal(int walletId, double amount) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            transactionService.saveTransaction(amount + " deducted from balance", TransactionType.WITHDRAWAL, walletId);
            return wallet.withdrawal(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public String transact(int senderWalletId, int receiverWalletId, double amount) {
        // find wallet by respective id's
        Wallet senderWallet = findWalletById(senderWalletId);
        Wallet receiverWallet = findWalletById(receiverWalletId);

        if (senderWallet != null && receiverWallet != null) {
            senderWallet.withdrawal(amount);
            receiverWallet.deposit(amount);
            transactionService.saveTransaction(amount + " transferred to userId " + receiverWalletId, TransactionType.TRANSFER, senderWalletId);
            transactionService.saveTransaction(amount + " received from  userId " + senderWalletId, TransactionType.TRANSFER, receiverWalletId);
            return "Transaction successful";
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    public double getBalance(int walletId) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            return wallet.getBalance();
        }
        throw new WalletNotFoundException("Wallet not found");
    }
}
