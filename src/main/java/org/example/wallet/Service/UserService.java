package org.example.wallet.Service;

import org.example.wallet.Exceptions.UsernameAlreadyRegisteredException;
import org.example.wallet.Models.User;
import org.example.wallet.Repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public String register(String username, String password) {
        try {
            userRepository.save(new User(username, password));
            return "User registered successfully";
        } catch (DataIntegrityViolationException e) {
            throw new UsernameAlreadyRegisteredException("Username already exists");
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        String retrievedPassword = userRepository.findPasswordByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(username + " no password associated with provided username")
                );

        return new org.springframework.security.core.userdetails.User(
                username,
                retrievedPassword,
                Collections.singletonList(() -> "ROLE_USER")
        );
    }
}
