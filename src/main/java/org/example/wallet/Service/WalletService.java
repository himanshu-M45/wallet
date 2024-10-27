package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Clients.CurrencyConverterClient;
import org.example.wallet.Enums.TransactionType;
import org.example.wallet.Exceptions.WalletNotFoundException;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CurrencyConverterClient currencyConverterClient;

    public Wallet findWalletById(int id) {
        return walletRepository.findById(id).orElse(null);
    }

    @Transactional
    public String deposits(int walletId, double amount) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            transactionService.saveWalletTransaction(amount, walletId, TransactionType.DEPOSIT);
            return wallet.deposit(amount);
        }
        throw new WalletNotFoundException("wallet not found");
    }

    @Transactional
    public String withdrawals(int walletId, double amount) {
        Wallet wallet = findWalletById(walletId);
        if (wallet != null) {
            transactionService.saveWalletTransaction(amount, walletId, TransactionType.WITHDRAWAL);
            return wallet.withdrawal(amount);
        }
        throw new WalletNotFoundException("wallet not found");
    }

    @Transactional
    public String transfer(int senderWalletId, int receiverWalletId, double amount) {
        // find wallet by respective id's
        Wallet senderWallet = findWalletById(senderWalletId);
        Wallet receiverWallet = findWalletById(receiverWalletId);

        if (senderWallet != null && receiverWallet != null) {
            // convert amount to receiver currency through gRPC service
            String senderCurrencyType = senderWallet.getCurrencyType().toString();
            String receiverCurrencyType = receiverWallet.getCurrencyType().toString();
            double convertedAmount = currencyConverterClient.convertCurrency(amount, senderCurrencyType, receiverCurrencyType);

            // perform transaction
            senderWallet.withdrawal(amount);
            receiverWallet.deposit(convertedAmount);

            // save transaction
            transactionService.saveTransferTransaction(amount, senderWalletId, receiverWalletId, TransactionType.TRANSFER);
            return "Transaction successful";
        }
        throw new WalletNotFoundException("wallet not found");
    }
}
