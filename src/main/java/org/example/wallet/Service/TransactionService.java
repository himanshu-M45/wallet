package org.example.wallet.Service;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Exceptions.InvalidTransactionTypeException;
import org.example.wallet.Exceptions.NoTransactionFoundException;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Models.TransferTransaction;
import org.example.wallet.Models.WalletTransaction;
import org.example.wallet.Repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Transaction> walletTransactions = transactionRepository.findWalletTransactionsByWalletId(walletId);
        List<Transaction> transferTransactions = transactionRepository.findTransferTransactionsByWalletId(walletId);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(walletTransactions);
        allTransactions.addAll(transferTransactions);

        if (allTransactions.isEmpty()) {
            throw new NoTransactionFoundException("no transactions found for walletId: " + walletId);
        }

        return allTransactions;
    }

    public List<Transaction> getTransactionsByType(int walletId, TransactionType transactionType) {
        if (transactionType == TransactionType.TRANSFER) {
            List<Transaction> transferTransaction = transactionRepository.findTransferTransactionsByTypeAndWalletId(transactionType, walletId);
            if (transferTransaction.isEmpty()) {
                throw new NoTransactionFoundException("no transactions found for walletId: " + walletId);
            }
            return transferTransaction;
        }
        if (transactionType == TransactionType.WITHDRAWAL || transactionType == TransactionType.DEPOSIT) {
            List<Transaction> walletTransaction = transactionRepository.findWalletTransactionsByTypeAndWalletId(transactionType, walletId);
            if (walletTransaction.isEmpty()) {
                throw new NoTransactionFoundException("no transactions found for walletId: " + walletId);
            }
            return walletTransaction;
        }
        throw new InvalidTransactionTypeException("invalid transaction type: " + transactionType);
    }
}
