package org.example.wallet.Service;

import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(String name, String password) {
        User user = new User(name, password);
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }
}
