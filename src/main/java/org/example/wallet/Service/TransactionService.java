package org.example.wallet.Service;

import org.example.wallet.DTO.TransactionDTO;
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
import java.util.Optional;

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

    public List<Transaction> getTransactionsByType(int walletId, String transactionType) {
        TransactionType type;
        try {
            type = TransactionType.valueOf(transactionType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidTransactionTypeException("invalid transaction type: " + transactionType);
        }

        switch (type) {
            case TRANSFER:
                List<Transaction> transferTransactions = transactionRepository.findTransferTransactionsByTypeAndWalletId(type, walletId);
                if (transferTransactions.isEmpty()) {
                    throw new NoTransactionFoundException("no transactions found for walletId: " + walletId);
                }
                return transferTransactions;
            case WITHDRAWAL:
            case DEPOSIT:
                List<Transaction> walletTransactions = transactionRepository.findWalletTransactionsByTypeAndWalletId(type, walletId);
                if (walletTransactions.isEmpty()) {
                    throw new NoTransactionFoundException("no transactions found for walletId: " + walletId);
                }
                return walletTransactions;
            default:
                throw new InvalidTransactionTypeException("invalid transaction type: " + transactionType);
        }
    }

    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionType(transaction.getTransactionType().name());
        dto.setTimestamp(transaction.getTimestamp());
        if (transaction instanceof WalletTransaction walletTransaction) {
            dto.setWalletId(Optional.of(walletTransaction.getWalletId()));
        }
        if (transaction instanceof TransferTransaction transferTransaction) {
            dto.setSenderId(Optional.of(transferTransaction.getSenderId()));
            dto.setReceiverId(Optional.of(transferTransaction.getReceiverId()));
        }
        return dto;
    }
}
