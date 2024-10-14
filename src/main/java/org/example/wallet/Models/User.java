package org.example.wallet.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public User(String name, String password) {
        if (name == null || name.isEmpty() || password == null || password.isEmpty() || !(wallet == null)) {
            throw new IllegalArgumentException("Name and password cannot be null or empty");
        }
        this.name = name;
        this.password = password;
        this.wallet = new Wallet();
    }

    public User() {
    }
}
