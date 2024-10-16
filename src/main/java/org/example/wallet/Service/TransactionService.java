package org.example.wallet.Service;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Models.Transaction;
import org.example.wallet.Repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void saveTransaction(int walletId, double deposit, double withdrawal, TransactionType transactionType, String reference) {
        transactionRepository.save(new Transaction(walletId, deposit, withdrawal, transactionType, reference));
    }

    public List<Transaction> getTransaction(int walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
