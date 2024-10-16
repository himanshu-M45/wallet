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

    @Column(name = "reference", nullable = false)
    private String reference;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonIgnore
    @Column(name = "wallet_id", nullable = false)
    private int walletId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @Column(name = "deposit", nullable = false)
    private double deposit;

    @Column(name = "withdrawal", nullable = false)
    private double withdrawal;

    public Transaction(int walletId, double deposit, double withdrawal, TransactionType transactionType, String reference) {
        this.walletId = walletId;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
        this.transactionType = transactionType;
        this.reference = reference;
        this.timestamp = new Date();
    }

    public Transaction() {}
}
