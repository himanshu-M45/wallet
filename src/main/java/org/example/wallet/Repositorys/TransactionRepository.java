package org.example.wallet.Repositorys;

import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT t FROM WalletTransaction t WHERE t.walletId = :walletId")
    List<Transaction> findWalletTransactionsByWalletId(@Param("walletId") int walletId);

    @Query("SELECT t FROM TransferTransaction t WHERE t.senderId = :walletId OR t.receiverId = :walletId")
    List<Transaction> findTransferTransactionsByWalletId(@Param("walletId") int walletId);

    @Query("SELECT t FROM WalletTransaction t WHERE t.walletId = :walletId AND t.transactionType = :transactionType")
    List<Transaction> findWalletTransactionsByTypeAndWalletId(@Param("transactionType") TransactionType transactionType, @Param("walletId") int walletId);

    @Query("SELECT t FROM TransferTransaction t WHERE (t.senderId = :walletId OR t.receiverId = :walletId) AND t.transactionType = :transactionType")
    List<Transaction> findTransferTransactionsByTypeAndWalletId(@Param("transactionType") TransactionType transactionType, @Param("walletId") int walletId);
}
