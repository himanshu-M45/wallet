package org.example.wallet.Service;

import org.example.wallet.Models.User;
import org.example.wallet.Models.Wallet;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WalletService walletService;

    public User registerUser(String name, String password) {
        User user = new User(name, password);
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public Wallet fetchUserWallet(Integer userId) {
        try {
            Integer walletId = userRepository.findWalletIdByUserId(userId);
            return walletService.findById(walletId);
        } catch (NullPointerException e) {
            throw new InvalidDataAccessApiUsageException("Wallet not found for user with id " + userId);
        }
    }
}
