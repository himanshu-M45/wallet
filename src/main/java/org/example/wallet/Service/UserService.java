package org.example.wallet.Service;

import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String register(String username, String password) {
        userRepository.save(new User(username, password));
        return "User registered successfully";
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
