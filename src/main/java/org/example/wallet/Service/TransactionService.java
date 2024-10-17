package org.example.wallet.Service;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Models.TransferTransaction;
import org.example.wallet.Models.WalletTransaction;
import org.example.wallet.Repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void saveWalletTransaction(double amount, int walletId, TransactionType transactionType) {
        WalletTransaction transaction = new WalletTransaction(amount, walletId, transactionType);
        transactionRepository.save(transaction);
    }

    public void saveTransferTransaction(double amount, int senderId, int receiverId, TransactionType transactionType) {
        TransferTransaction transaction = new TransferTransaction(amount, senderId, receiverId, transactionType);
        transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByWalletId(int walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
