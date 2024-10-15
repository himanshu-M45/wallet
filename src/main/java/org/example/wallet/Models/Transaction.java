package org.example.wallet.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.TransactionType;

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

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    @JsonIgnore
    private Wallet wallet;

    public Transaction(String message, TransactionType transactionType, Wallet wallet) {
        this.message = message;
        this.transactionType = transactionType;
        this.wallet = wallet;
    }

    public Transaction() {}
}
