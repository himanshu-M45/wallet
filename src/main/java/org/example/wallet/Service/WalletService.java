package org.example.wallet.Service;

import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public Wallet findById(Integer id) {
        return walletRepository.findById(id).orElse(null);
    }
}
