package org.example.wallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.TransactionType;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonIgnore
    @Column(name = "wallet_id", nullable = false)
    private int walletId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    public Transaction(String message, TransactionType transactionType, int walletId) {
        this.message = message;
        this.transactionType = transactionType;
        this.walletId = walletId;
        this.timestamp = new Date();
    }

    public Transaction() {}
}
