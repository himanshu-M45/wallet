package org.example.wallet.DTO;

import lombok.Data;

@Data
public class WalletDTO {
    private double amount;
    private int receiverWalletId;
}
