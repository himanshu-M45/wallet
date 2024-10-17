package org.example.wallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.TransactionType;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @Column(name = "amount", nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = new Date();
    }

    public Transaction() {}
}
