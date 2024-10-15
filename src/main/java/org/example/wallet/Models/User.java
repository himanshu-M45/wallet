package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    private String username;
    @Getter
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public User(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || !(wallet == null)) {
            throw new IllegalArgumentException("Name and password cannot be null or empty");
        }
        this.username = username;
        this.password = password;
        this.wallet = new Wallet();
    }

    public User() {
    }

}
