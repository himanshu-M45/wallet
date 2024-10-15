package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            wallet.addTransaction(amount + " added to balance", TransactionType.DEPOSIT);
            return wallet.deposit(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public Double withdrawal(int userId, double amount) {
        int walletId = userService.findWalletId(userId);
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            wallet.addTransaction(amount + " deducted from balance", TransactionType.WITHDRAWAL);
            return wallet.withdrawal(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public String transact(int senderId, int receiverId, double amount) {
        // retrieve wallet id of sender and receiver
        int senderWalletId = userService.findWalletId(senderId);
        int receiverWalletId = userService.findWalletId(receiverId);

        // find wallet by respective id's
        Wallet senderWallet = findWalletById(senderWalletId);
        Wallet receiverWallet = findWalletById(receiverWalletId);

        if (senderWallet != null && receiverWallet != null) {
            senderWallet.withdrawal(amount);
            receiverWallet.deposit(amount);
            senderWallet.addTransaction(amount + " transferred to userId " + receiverId, TransactionType.TRANSFER);
            receiverWallet.addTransaction(amount + " received from  userId " + senderId, TransactionType.TRANSFER);
            return "Transaction successful";
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

    public List<Transaction> getTransactions(int userId) {
        int walletId = userService.findWalletId(userId);
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            return wallet.getTransactions();
        }
        throw new WalletNotFoundException("Wallet not found");
    }
}
