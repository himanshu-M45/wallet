package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Double deposit(int userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIsNotRegisteredException("User not found"));
        return user.deposit(amount);
    }

    @Transactional
    public Double withdraw(int userId, double amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIsNotRegisteredException("User not found"));
        return user.withdraw(amount);
    }

    public double getBalance(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserIsNotRegisteredException("User not found"));
        return user.getBalance();
    }
}
