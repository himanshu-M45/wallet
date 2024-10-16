package org.example.wallet.DTO;

import lombok.Data;
import org.example.wallet.Enums.CurrencyType;

@Data
public class UserDTO {
    private String name;
    private String password;
    private CurrencyType currencyType;
}
