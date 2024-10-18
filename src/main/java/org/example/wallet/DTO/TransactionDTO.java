package org.example.wallet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionDTO {
    private int id;
    private double amount;
    private Optional<Integer> walletId = Optional.empty();
    private Optional<Integer> senderId = Optional.empty();
    private Optional<Integer> receiverId = Optional.empty();
    private String transactionType;
    private Date timestamp;
}
