package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Enums.CurrencyType;
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
            transactionService.saveTransaction(walletId, amount, 0, TransactionType.DEPOSIT, "SELF");
            return wallet.deposit(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public Double withdrawal(int walletId, double amount) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            transactionService.saveTransaction(walletId, 0, amount, TransactionType.WITHDRAWAL, "SELF");
            return wallet.withdrawal(amount);
        }
        throw new WalletNotFoundException("Wallet not found");
    }

    @Transactional
    public String transfer(int senderWalletId, int receiverWalletId, double amount) {
        // find wallet by respective id's
        Wallet senderWallet = findWalletById(senderWalletId);
        Wallet receiverWallet = findWalletById(receiverWalletId);

        if (senderWallet != null && receiverWallet != null) {
            // convert amount to receiver currency
            CurrencyType receiverCurrency = receiverWallet.getCurrencyType();
            double convertedAmount = senderWallet.getCurrencyType().convertTo(amount, receiverCurrency);

            // perform transaction
            senderWallet.withdrawal(amount);
            receiverWallet.deposit(convertedAmount);

            // save transaction
            transactionService.saveTransaction(senderWalletId, 0, amount, TransactionType.TRANSFER, "TO: " + receiverWalletId);
            transactionService.saveTransaction(receiverWalletId, convertedAmount, 0, TransactionType.TRANSFER, "FROM: " + senderWalletId);
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
