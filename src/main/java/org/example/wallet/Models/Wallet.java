package org.example.wallet.Models;

import jakarta.persistence.*;
import org.example.wallet.Exceptions.InsufficientBalanceException;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double balance = 0.0;

    protected Wallet() {}

    Double deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative or zero");
        }
        this.balance += amount;
        return this.balance;
    }

    Double withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative or zero");
        }
        if (this.balance - amount < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance -= amount;
        return this.balance;
    }

    double getBalance() {
        return balance;
    }
}
