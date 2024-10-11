package org.example.wallet.Service;

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
        User user = new User(name, password);
        User savedUser = userRepository.save(user);
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
}
