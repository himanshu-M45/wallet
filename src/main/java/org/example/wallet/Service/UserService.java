package org.example.wallet.Service;

import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String password) {
        User user = new User(name, password);
        return userRepository.save(user);
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deposit(int userId, double amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserIsNotRegisteredException("User not found");
        }
        user.deposit(amount);
        userRepository.save(user);
    }

    public void withdraw(int userId, double amount) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserIsNotRegisteredException("User not found");
        }
        user.withdraw(amount);
        userRepository.save(user);
    }

    public double getBalance(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserIsNotRegisteredException("User not found");
        }
        return user.getBalance();
    }
}
