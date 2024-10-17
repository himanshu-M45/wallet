package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.TransactionType;

@Entity
@Table(name = "wallet_transactions")
@Getter
public class WalletTransaction extends Transaction {
    @Column(name = "wallet_id", nullable = false)
    private int walletId;
    public WalletTransaction(double amount, int walletId, TransactionType transactionType) {
        super(amount, transactionType);
        this.walletId = walletId;
    }

    public WalletTransaction() {}
}
