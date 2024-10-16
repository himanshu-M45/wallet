package org.example.wallet.Models;

import jakarta.persistence.*;
import org.example.wallet.Enums.CurrencyType;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public User(String username, String password, CurrencyType currencyType) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || currencyType == null || !(wallet == null)) {
            throw new IllegalArgumentException("Name and password cannot be null or empty");
        }
        this.username = username;
        this.password = password;
        this.wallet = new Wallet(currencyType);
    }

    public User() {
    }

}
