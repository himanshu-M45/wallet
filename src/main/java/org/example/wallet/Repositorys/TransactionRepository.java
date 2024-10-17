package org.example.wallet.Repositorys;

import org.example.wallet.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM Transaction t WHERE t.walletId = :walletId OR t.receiverId = :walletId")
    List<Transaction> findByWalletId(@Param("walletId") int walletId);
}
