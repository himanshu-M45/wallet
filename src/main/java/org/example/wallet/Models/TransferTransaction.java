package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.TransactionType;

@Entity
@Table(name = "transfer_transactions")
@Getter
public class TransferTransaction extends Transaction {
    @Column(name = "sender_id")
    private int senderId;
    @Column(name = "receiver_id", nullable = false)
    private int receiverId;

    public TransferTransaction(double amount, int senderId, int receiverId, TransactionType transactionType) {
        super(amount, transactionType);
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public TransferTransaction() {}
}
