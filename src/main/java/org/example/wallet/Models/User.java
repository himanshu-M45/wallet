package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wallet wallet;

    public User(String name, String password) {
        if (name == null || name.isEmpty() || password == null || password.isEmpty() || !(wallet == null)) {
            throw new IllegalArgumentException("Name and password cannot be null or empty");
        }
        this.name = name;
        this.password = password;
        this.wallet = new Wallet();
        this.wallet.setUser(this);
    }

    public User() {
    }
}
