package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.wallet.Exceptions.InsufficientBalanceException;

@Entity
@Table(name = "wallets")
@Getter
public class Wallet {
    @Id
    private Long id;
    private double balance;

    protected Wallet() {
        this.balance = 0.0;
    }

    void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount cannot be negative or zero");
        }
        this.balance += amount;
    }

    void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount cannot be negative or zero");
        }
        if (this.balance - amount < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        this.balance -= amount;
    }
}
