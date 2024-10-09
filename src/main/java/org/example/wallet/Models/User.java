package org.example.wallet.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.wallet.Exceptions.CannotOwnWalletWithoutProperUser;


@Entity
@Getter
@Setter
@Table(name = "qœusers")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wallet wallet;

    public User() {
    }

    public void ownWallet() {
        if (!isValidUser()) {
            throw new CannotOwnWalletWithoutProperUser("cannot have a wallet without proper user");
        }
        this.wallet = new Wallet();
    }

    private boolean isValidUser() {
        return this.name != null && !this.name.isEmpty() &&
                this.password != null && !this.password.isEmpty() &&
                this.id != null && this.wallet == null;
    }

    public double getBalance() {
        return wallet.getBalance();
    }
}
