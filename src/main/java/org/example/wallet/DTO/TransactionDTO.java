package org.example.wallet.DTO;

import lombok.Data;

@Data
public class TransactionDTO {
    private double amount;
    private int receiverId;
}
