package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Enums.CurrencyType;
import org.example.wallet.Exceptions.InsufficientBalanceException;
import org.example.wallet.Exceptions.InvalidAmountEnteredException;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double balance = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    @Getter
    private CurrencyType currencyType;

    protected Wallet(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public Wallet() {}

    public Double deposit(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountEnteredException("Deposit amount cannot be negative or zero");
        }
        this.balance += amount;
        return this.balance;
    }

    public Double withdrawal(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountEnteredException("Withdrawal amount cannot be negative or zero");
        }
        if (this.balance - amount < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance -= amount;
        return this.balance;
    }

    public double getBalance() {
        return balance;
    }
}
