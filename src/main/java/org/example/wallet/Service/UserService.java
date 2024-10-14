package org.example.wallet.Service;

import jakarta.transaction.Transactional;
import org.example.wallet.Exceptions.UserIsNotRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public int registerUser(String name, String password) {
        User savedUser = userRepository.save(new User(name, password));
        try {
            Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            return (Integer) idField.get(savedUser);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to retrieve user ID", e);
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

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
