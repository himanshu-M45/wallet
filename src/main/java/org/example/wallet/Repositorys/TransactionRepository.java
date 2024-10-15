package org.example.wallet.Repositorys;

import org.example.wallet.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByWalletId(int walletId);
}
